package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.*;
import com.xhkj.server.energy.dao.mybatis.vo.*;
import com.xhkj.server.energy.service.TecolCurveService;
import com.xhkj.server.energy.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
public class TecolCurveServiceImpl implements TecolCurveService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WeatherDataStalDao weatherDataStalDao;

    @Autowired
    StandDataDao standDataDao;

    @Autowired
    TecolCurveCulDataDao tecolCurveCulDataDao;

    @Autowired
    TecolCurveDataDao tecolCurveDataDao;

    @Autowired
    DataBasicDao dataBasicDao;

    @Autowired
    ValveCompanyDao valveCompanyDao;

    @Override
    public TecolCurveData getTecolCurveData() {
        String dt = DateUtils.toString(new Date(), "yyyyMMdd");//yyyyMMdd
        WeatherDataStal weatherDataStal = weatherDataStalDao.getWeatherStalCurrent(dt);
        if (weatherDataStal == null) {
            return null;
        }
        Short tem = weatherDataStal.getTem().shortValue();//这里取出来最新时实的温度
        //如果因为表里最高5，最低-24，如果超过这两个，取边界值。
        if (tem > 5) {
            tem = 5;
        }
        if (tem < -24) {
            tem = -24;
        }
        //通过时实的温度在data_tecol_curvege表里取出来TE1到TE4。
        return tecolCurveDataDao.get(tem);
    }

    //@PostConstruct
    @Scheduled(cron = "0 0 0/1 * * ? ")//每一小时执行一次。取今日最后一条时实天气，
    public void updateTecolCurveCul() {
        TecolCurveData tecolCurveData = getTecolCurveData();

        Float TE1 = tecolCurveData.getTe1();
        Float TE2 = tecolCurveData.getTe2();
        Float TE3 = tecolCurveData.getTe3();
        Float TE4 = tecolCurveData.getTe4();

        List<ValveCompany> companyList = valveCompanyDao.getAll();
        for (ValveCompany company : companyList) {
            Integer companyId = company.getCompanyId();
            TecolCurveCulData tecolCurveCulData = null;
            TecolCurveCulDataKey tecolCurveCulDataKey = new TecolCurveCulDataKey();
            tecolCurveCulDataKey.setCompanyId(companyId);
            List<StandData> standDataList = standDataDao.getStandListByCompanyId(companyId);
            for (StandData standData : standDataList) {
                tecolCurveCulDataKey.setStandId(standData.getStationId());
                DataBasic dataBasic = dataBasicDao.getLastLegal(companyId, standData.getStandId());
                tecolCurveCulData = tecolCurveCulDataDao.get(tecolCurveCulDataKey);
                if (tecolCurveCulData != null) {//如果没有则插入
                    tecolCurveCulData.setTe1Cul(TE1 - dataBasic.getTe1());
                    tecolCurveCulData.setTe2Cul(TE2 - dataBasic.getTe2());
                    tecolCurveCulData.setTe3Cul(TE3 - dataBasic.getTe3());
                    tecolCurveCulData.setTe4Cul(TE4 - dataBasic.getTe4());

                    tecolCurveCulDataDao.update(tecolCurveCulData);
                } else {
                    tecolCurveCulData = new TecolCurveCulData();
                    tecolCurveCulData.setCompanyId(companyId);
                    tecolCurveCulData.setStandId(standData.getStandId());
                    tecolCurveCulData.setStandName(standData.getStandName());
                    tecolCurveCulData.setTe1Cul(TE1 - dataBasic.getTe1());
                    tecolCurveCulData.setTe2Cul(TE2 - dataBasic.getTe2());
                    tecolCurveCulData.setTe3Cul(TE3 - dataBasic.getTe3());
                    tecolCurveCulData.setTe4Cul(TE4 - dataBasic.getTe4());

                    tecolCurveCulDataDao.save(tecolCurveCulData);
                }
            }
        }
    }

    public static void main(String[] args) {
        Date oneHourDaysAgoDate = DateUtils.addHours(new Date(), -1); //这里往前取一个小时
        String dt = DateUtils.toString(oneHourDaysAgoDate, "yyyyMMdd");//yyyyMMdd
        int a = 12231;
        float b = Float.valueOf(a)/10000;
        System.out.println(b);
    }

}
