package com.xhkj.server.energy.service;

import com.xhkj.server.energy.dao.mybatis.vo.StandData;

import java.util.List;

public interface StandDataService {
    List<StandData> getAll();

    StandData getById(int companyId, String standId);

    void update(StandData standData);

    void saveOrUpdate(StandData standData);
}
