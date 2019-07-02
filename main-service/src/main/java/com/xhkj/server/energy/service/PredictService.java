package com.xhkj.server.energy.service;

import com.xhkj.server.energy.dao.mybatis.vo.PredictData;

public interface PredictService {
    PredictData getThePredict();

    Boolean updateHaErBinSelective(PredictData predictData);
}
