package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.*;
import com.xhkj.server.energy.dao.mybatis.vo.*;
import com.xhkj.server.energy.page.Page;
import com.xhkj.server.energy.service.PredictHistoryService;
import com.xhkj.server.energy.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PredictHistoryServiceImpl implements PredictHistoryService {

    @Autowired
    private PredictHistoryDataDao predictHistoryDataDao;
    @Autowired
    CollectDataDayDao collectDataDayDao;
    @Autowired
    WeatherDataDao weatherDataDao;
    @Autowired
    PredictDataDao predictDataDao;
    @Autowired
    ValveCompanyDao valveCompanyDao;
    @Autowired
    StationDataDao stationDatadao;
    @Autowired
    StandDataDao standDataDao;
    @Autowired
    PredictStationDao predictStationDao;


    public List<PredictHistoryData> getPredictHistoryDatas(Page<PredictHistoryData> page) {
        return predictHistoryDataDao.getPredictHistoryList(page);
    }

    public PredictHistoryData getByOpTime(Date date) {
        return predictHistoryDataDao.getByOpTime(date);
    }

    public Integer updateByOpTimeSelective(PredictHistoryData predictHistoryData) {
        return predictHistoryDataDao.updateByOpTimeSelective(predictHistoryData);
    }

    public Integer insert(PredictHistoryData predictHistoryData) {
        return predictHistoryDataDao.insert(predictHistoryData);
    }

    //@PostConstruct
    @Scheduled(cron = "0 20 00/3 * * ? ")//每天00点20分触发,然后每3个小时执行
    public void ScheduledUpdateHistorySelective() {
        Date today = DateUtils.getYmdDate(new Date());
        Date yesterdayDate = DateUtils.dayAdd(-1, new Date());
        Date twoDaysAgoDate = DateUtils.dayAdd(-2, new Date());
        Date in3Days = DateUtils.dayAdd(3, new Date());

        PredictData basicParam = predictDataDao.getThePredict();
        PredictHistoryData yesterdayData = predictHistoryDataDao.getByOpTime(yesterdayDate);

        float k, L, F, Tn, Tw1, k1, k2, Q2, Q3, Q4, Q5, Q6, Tpj2, Tpj3, Tpj4, Tpj5, yesK2, yesQ2, yesQ3;
        //设计热指标，data_para表中的q
        k = basicParam.getQ();
        L = basicParam.getL();
        F = basicParam.getF();
        Tn = basicParam.getTn();
        Tw1 = basicParam.getTw1();
        //计算Q2
        Q2 = collectDataDayDao.getTotalHeat(yesterdayDate);
        //初始化昨天的Q3，若为空则用Q2.初始化今天的k2，若为空则用k（data_para表中的q）
        if (yesterdayData == null) {
            yesQ3 = Q2;
            yesK2 = k;
            yesQ2 = collectDataDayDao.getTotalHeat(twoDaysAgoDate);
        } else {
            yesQ3 = yesterdayData.getQ3();
            yesK2 = yesterdayData.getK2();
            yesQ2 = yesterdayData.getQ2();
        }
        //修正系数，k1=Q2/昨天记录当中的Q3
        k1 = Q2 / yesQ3;
        //如果k1 < 0.95或k1 > 1.05，则k2 = k。否则，k2 = k * k1 * 昨天（上一条）记录当中的q2
        if (k1 < 0.95 || k1 > 1.05) {
            k2 = k;
        } else {
            k2 = k * k1 * yesK2;
        }
        //取出今天至大后天的天气预测信息
        Map<String, String> params = new HashMap<>();
        params.put("beginTime", DateUtils.toString(today, "yyyyMMdd"));
        params.put("endTime", DateUtils.toString(in3Days, "yyyyMMdd"));
        List<WeatherData> avgTemList = weatherDataDao.getAvgTemList(params);
        //根据天气计算出几天的耗热量，Tpj2是当日的日平均温度，Tpj3是明天的日平均温度......
        Tpj2 = avgTemList.get(3).getTeA();
        Tpj3 = avgTemList.get(2).getTeA();
        Tpj4 = avgTemList.get(1).getTeA();
        Tpj5 = avgTemList.get(0).getTeA();
        //今天预测耗热量Q3（GJ），明天预测耗热量Q4（GJ）......
        Q3 = L * F * k2 * (Tn - Tpj2) / (Tn - Tw1);
        Q4 = L * F * k2 * (Tn - Tpj3) / (Tn - Tw1);
        Q5 = L * F * k2 * (Tn - Tpj4) / (Tn - Tw1);
        Q6 = L * F * k2 * (Tn - Tpj5) / (Tn - Tw1);
        //设置数据
        PredictHistoryData predictHistoryData
                = new PredictHistoryData(today, k, k1, k2, Q2, Q3, Q4, Q5, Q6, Tpj2, Tpj3, Tpj4, Tpj5);
        //若有记录则更新、若无则插入
        if (predictHistoryDataDao.getByOpTime(predictHistoryData.getOpTime()) == null) {
            predictHistoryDataDao.insert(predictHistoryData);
        } else {
            predictHistoryDataDao.updateByOpTimeSelective(predictHistoryData);
        }

        //这里设置一下换站站预测历史，和上面的只是F变成各个换热站的设计面积
        List<ValveCompany> companyList = valveCompanyDao.getAll();
        for (ValveCompany company : companyList) {
            Integer companyId = company.getCompanyId();

            StationDataKey stationDataKey = new StationDataKey();
            stationDataKey.setCompanyId(companyId);
            List<StationData> stationDataList = stationDatadao.getStationListByCompanyID(companyId);
            List<StandData> standDataList = standDataDao.getStandListByCompanyId(companyId);

            for (StationData stationData : stationDataList) {
                Integer stationArea = 0; //因为数据库里取出来的是int，也就是平方米，这里算的时候要用万平方米
                for (StandData standData : standDataList) {
                    if(standData.getStationId().equals(stationData.getStationId())) {
                        stationArea += standData.getRealArea()==null?0:standData.getRealArea();
                    }
                }
                F = Float.valueOf(stationArea)/10000; //这里把平方米算成万平方米
                Q3 = L * F * k2 * (Tn - Tpj2) / (Tn - Tw1);
                Q4 = L * F * k2 * (Tn - Tpj3) / (Tn - Tw1);
                Q5 = L * F * k2 * (Tn - Tpj4) / (Tn - Tw1);
                Q6 = L * F * k2 * (Tn - Tpj5) / (Tn - Tw1);
                //设置数据
                PredictStation predictStation
                        = new PredictStation(today,companyId,stationData.getStationId(),stationData.getStationName(), k, k1, k2, Q2, Q3, Q4, Q5, Q6, Tpj2, Tpj3, Tpj4, Tpj5);
                //若有记录则更新、若无则插入
                PredictStationKey predictStationKey = new PredictStationKey();
                predictStationKey.setOpTime(predictStation.getOpTime());
                predictStationKey.setStationId(stationData.getStationId());
                if (predictStationDao.get(predictStationKey) == null) {
                    predictStationDao.save(predictStation);
                } else {
                    predictStationDao.update(predictStation);
                }
            }

        }


    }
}
