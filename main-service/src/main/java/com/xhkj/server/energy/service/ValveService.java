package com.xhkj.server.energy.service;

import com.xhkj.server.energy.dao.mybatis.vo.Valve;

import java.util.List;

public interface ValveService {

    List<Valve> getAllLastBatchNumber(String projectId);
    List<Valve> getAllLastBatchNumberForTime(String projectId,String searchTime);
}
