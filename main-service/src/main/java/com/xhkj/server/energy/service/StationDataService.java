package com.xhkj.server.energy.service;

import com.xhkj.server.energy.dao.mybatis.vo.StationData;

import java.util.List;

public interface StationDataService {
    List<StationData> getAll();

    StationData getById(int companyId, String stationId);

    void update(StationData stationData);

    void saveOrUpdate(StationData stationData);
}
