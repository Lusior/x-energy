package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.PredictHistoryData;
import com.xhkj.server.energy.page.Page;

import java.util.Date;
import java.util.List;

public interface PredictHistoryDataDao extends Dao<PredictHistoryData, Date> {

    ////*******自定义开始********//
    List<PredictHistoryData> getPredictHistoryList(Page<PredictHistoryData> page);

    Integer updateByOpTimeSelective(PredictHistoryData predictHistoryData);

    Integer insert(PredictHistoryData predictHistoryData);

    PredictHistoryData getByOpTime(Date yesterdayDate);
    //**********自定义结束*****////

}
