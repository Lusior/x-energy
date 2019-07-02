package com.xhkj.server.energy.task;

import com.xhkj.server.energy.dao.GroupDataDao;
import com.xhkj.server.energy.dao.mybatis.vo.*;
import com.xhkj.server.energy.service.*;
import com.xhkj.server.energy.utils.BeanUtils;
import com.xhkj.server.energy.utils.DateUtils;
import com.xhkj.server.energy.utils.HIPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DataBasicHandler implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(DataBasicHandler.class);

    private static final int blockQueueSize = 5000;
    private static final int threadCount = Runtime.getRuntime().availableProcessors() * 8;// 线程数量如果不为1的话，业务处理会有问题
    private static final AtomicInteger threadId = new AtomicInteger(0);
    private volatile Boolean isRunning = true;
    // 消息队列
    private final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(blockQueueSize);
    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(threadCount, threadCount,
            0L, TimeUnit.SECONDS,
            blockingQueue,
            r -> new Thread(r, "DataBasic_handler_" + threadId.getAndIncrement()),
            (r, executor) -> {
                throw new RuntimeException("队列已满");
            });

    @Autowired
    private DataBasicService dataBasicService;
    @Autowired
    private CollectDataService collectDataService;
    @Autowired
    private StationDataService stationDataService;
    @Autowired
    private StandDataService standDataService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private GroupDataDao groupDataDao;
    @Value("${energy.create-fake-data-ip}")
    String createFakeDataIp;

    @Override
    public void afterPropertiesSet() {
        String ip = HIPUtil.get(true);
        boolean equals = createFakeDataIp.equals(ip);
        logger.info("runDataHandler=" + equals);
        if (!equals) {
            return;
        }
        new Thread(() -> {
            try {
                while (isRunning) {
                    try {
                        Thread.sleep(5000);
                        logger.debug("lockingQueue.size() = " + blockingQueue.size() + " threadPool.getActiveCount() = " + threadPool.getActiveCount());
                        if (blockingQueue.size() == 0 && threadPool.getActiveCount() == 0) {
                            reload();
                        }
                    } catch (Exception e) {
                        logger.error("reload_error", e);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }).start();
    }

    private void reload() {
        List<DataBasic> oldestList = dataBasicService.getNoProcessedList(500);
        for (DataBasic dataBasic : oldestList) {
            threadPool.execute(() -> {
                try {
//                    long start = System.currentTimeMillis();
                    convert(dataBasic);
//                    logger.info(Thread.currentThread().getName() + ",time1: " + (System.currentTimeMillis() - start));
                } catch (Exception e) {
                    logger.error("DataBasicHandler reload error=", e);
                } finally {
                    dataBasicService.updateProcessedStatus(dataBasic);
                }
            });
        }
    }

    /**
     * 该方法同之前的触发器功能
     * 将推送过来的数据进行分表，非法数据修复等处理
     *
     * @param dataBasic 推送数据
     */
    private void convert(DataBasic dataBasic) {
        if (dataIllegal(dataBasic)) {
            logger.warn("dataIllegal=" + dataBasic);
            if (canRepair(dataBasic)) {
                // 修复数据逻辑
                dataBasic = repairData(dataBasic);
                if (dataBasic == null) {
                    return;
                }
            } else {
                return;
            }
        }
        // 插入表 收集表 data_collect
        CollectData collectData = new CollectData();
        BeanUtils.copyProperties(dataBasic, collectData, false);
        Price priceData = priceService.getPriceData();
        collectData.setJqiPrice(priceData.getJqiPrice());
        collectData.setQqiPrice(priceData.getQqiPrice());
        collectData.setFt3qPrice(priceData.getFt3qPrice());
        String s = DateUtils.toString(collectData.getOpTime(), "yyMMddHHmm");
        collectData.setBatchNumber(Integer.parseInt(s));
        collectDataService.save(collectData);
        // 插入表 机组表 data_stand   //这里会把加入的面积等信息更新成null,在里面处理了
        StandData standData = new StandData();
        BeanUtils.copyProperties(dataBasic, standData, false);
        standDataService.saveOrUpdate(standData);
        // 插入表 换热站 data_station  这里会把加入的联系人等信息更新成null,在里面处理了
        StationData stationData = new StationData();
        BeanUtils.copyProperties(dataBasic, stationData, false);
        stationDataService.saveOrUpdate(stationData);
        // 插入表 分所表 data_group
        GroupData groupData = new GroupData();
        BeanUtils.copyProperties(dataBasic, groupData, false);
        groupDataDao.saveOrUpdate(groupData);
    }

    private DataBasic repairData(DataBasic dataBasic) {
        DataBasic lastLegal = dataBasicService.getLastLegal(dataBasic.getCompanyId(), dataBasic.getStandId());
        if (lastLegal != null) {
            DataBasic db = new DataBasic();
            BeanUtils.copyProperties(lastLegal, db, false);
            db.setId(dataBasic.getId());
            db.setOpTime(dataBasic.getOpTime());
            return db;
        }
        return null;
    }

    private static final String illegalStr = "9999";

    private boolean dataIllegal(DataBasic dataBasic) {
        return idIllegal(dataBasic) || ptIllegal(dataBasic);
    }

    private boolean ptIllegal(DataBasic dataBasic) {
        return (dataBasic.getPt1() <= 0 || dataBasic.getPt1() >= 1) &&
                (dataBasic.getPt2() <= 0 || dataBasic.getPt2() >= 1)/* &&
                (dataBasic.getPt3() <= 0 || dataBasic.getPt3() >= 1)*/;
    }

    private boolean idIllegal(DataBasic dataBasic) {
        return dataBasic.getStandId().contains(illegalStr) ||
                dataBasic.getStationId().contains(illegalStr) ||
                dataBasic.getGroupId().contains(illegalStr);
    }

    private boolean canRepair(DataBasic dataBasic) {
        return (!idIllegal(dataBasic)) && ptIllegal(dataBasic);
    }
}
