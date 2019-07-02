package com.xhkj.server.energy.service;

import com.xhkj.server.energy.dao.mybatis.vo.PredictHistoryData;
import com.xhkj.server.energy.page.Page;

import java.util.Date;
import java.util.List;

public interface PredictHistoryService {
    List<PredictHistoryData> getPredictHistoryDatas(Page<PredictHistoryData> page);

    PredictHistoryData getByOpTime(Date date);

    Integer updateByOpTimeSelective(PredictHistoryData predictHistoryData);

    Integer insert(PredictHistoryData predictHistoryData);
}
