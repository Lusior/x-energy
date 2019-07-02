package com.xhkj.server.energy.task;

import com.xhkj.server.energy.config.EnergyProperties;
import com.xhkj.server.energy.dao.DataBasicDao;
import com.xhkj.server.energy.dao.mybatis.vo.DataBasic;
import com.xhkj.server.energy.utils.BeanUtils;
import com.xhkj.server.energy.utils.DateUtils;
import com.xhkj.server.energy.utils.HIPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FakeDataTask {

    private static final String pattern = "MM月dd日 HH时mm分ss秒";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataBasicDao dataBasicDao;
    @Autowired
    private EnergyProperties energyProperties;

    private static List<String[]> baseDataList = new ArrayList<>();

    static {
        baseDataList.add(new String[]{"1", "A1", "桥北站低区", "B1"});
        baseDataList.add(new String[]{"1", "A2", "桥北站高区", "B1"});
        baseDataList.add(new String[]{"1", "A3", "工程小区", "B2"});
        baseDataList.add(new String[]{"1", "A4", "春明小区", "B3"});
        baseDataList.add(new String[]{"1", "A5", "电台低区", "B4"});
        baseDataList.add(new String[]{"1", "A6", "电台高区", "B4"});
        baseDataList.add(new String[]{"1", "A7", "电台中区", "B4"});
        baseDataList.add(new String[]{"1", "A8", "教化小区", "B5"});
        baseDataList.add(new String[]{"1", "A9", "曲线小区", "B6"});
        baseDataList.add(new String[]{"1", "A10", "工务机械段", "B7"});
        baseDataList.add(new String[]{"1", "A11", "西桥小区", "B8"});
        baseDataList.add(new String[]{"1", "A12", "西桥低区", "B9"});
        baseDataList.add(new String[]{"1", "A13", "西桥高区", "B10"});

        baseDataList.add(new String[]{"2", "A1", "教授家园北区", "B1"});
        baseDataList.add(new String[]{"2", "A2", "教授家园南区", "B2"});
        baseDataList.add(new String[]{"2", "A3", "环卫车队", "B3"});
        baseDataList.add(new String[]{"2", "A4", "东方哥德堡低区", "B4"});
        baseDataList.add(new String[]{"2", "A5", "东方哥德堡高区", "B4"});
        baseDataList.add(new String[]{"2", "A6", "裕发新城散热区", "B5"});
        baseDataList.add(new String[]{"2", "A7", "裕发新城地热区", "B5"});
        baseDataList.add(new String[]{"2", "A8", "派斯菲克低区", "B6"});
        baseDataList.add(new String[]{"2", "A9", "派斯菲克高区", "B6"});
    }

    private void createFake() {
        Date opTime = new Date();
        for (String[] strings : baseDataList) {
            int companyId = Integer.parseInt(strings[0]);
            String standId = strings[1];
            DataBasic lastLegal = dataBasicDao.getLastLegal(companyId, standId);
            if (lastLegal != null) {
                DataBasic db = new DataBasic();
                BeanUtils.copyProperties(lastLegal, db, false);
                db.setId(null);// 自增
                db.setOpTime(opTime);
                db.setProcessed(null);
                db.setJqi(db.getJqi() + 1);
                db.setQqi(db.getQqi() + 1);
                db.setFt3q(db.getFt3q() + 1);
                dataBasicDao.save(db);
            }
        }
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void everyTenMinutes() {
        String createFakeDataIp = energyProperties.getCreateFakeDataIp();
        String ip = HIPUtil.get(true);
        boolean equals = createFakeDataIp.equals(ip);
        print("everyTenMinutes ip = " + ip + ", execute = " + equals);
        if (equals) {
            createFake();
        }
    }

    private void print(String prefix) {
        String s = DateUtils.toString(new Date(), pattern);
        System.out.println(prefix + ":" + s);
        logger.info(prefix + ":" + s);
    }
}
