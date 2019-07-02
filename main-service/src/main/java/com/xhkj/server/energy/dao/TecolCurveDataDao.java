package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.TecolCurveData;
import java.lang.Short;

public interface TecolCurveDataDao extends Dao<TecolCurveData, Short> {

    ////*******自定义开始********//
    TecolCurveData getByTemperature(Short temperature);
    //**********自定义结束*****////

}
