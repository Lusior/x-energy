package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.Valve;

import java.lang.Integer;
import java.util.List;
import java.util.Map;

public interface ValveDao extends Dao<Valve, Integer> {

    ////*******自定义开始********//

    int getMaxBatchNumber(String projectId);
    List<Valve> getAllLastBatchNumber(String projectId);
    List<Valve> getAllLastBatchNumberForTime(String projectId, String searchTime);
    //**********自定义结束*****////

}
