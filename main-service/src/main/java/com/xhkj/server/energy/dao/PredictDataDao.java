package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.PredictData;

public interface PredictDataDao extends Dao<PredictData, String> {

    ////*******自定义开始********//

    PredictData getThePredict();

    Integer updateHaErBinSelective(PredictData thePredict);

    //**********自定义结束*****////

}
