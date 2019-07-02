package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.PredictStation;
import com.xhkj.server.energy.dao.mybatis.vo.PredictStationKey;

import java.util.Date;
import java.util.List;

public interface PredictStationDao extends Dao<PredictStation, PredictStationKey> {

    ////*******自定义开始********//
    List<PredictStation> getByDate(Date date);
    //**********自定义结束*****////

}
