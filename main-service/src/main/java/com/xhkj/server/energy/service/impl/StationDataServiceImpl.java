package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.StationDataDao;
import com.xhkj.server.energy.dao.mybatis.vo.StandData;
import com.xhkj.server.energy.dao.mybatis.vo.StandDataKey;
import com.xhkj.server.energy.dao.mybatis.vo.StationData;
import com.xhkj.server.energy.dao.mybatis.vo.StationDataKey;
import com.xhkj.server.energy.service.StationDataService;
import com.xhkj.server.energy.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationDataServiceImpl implements StationDataService {

    @Autowired
    private StationDataDao stationDataDao;

    @Override
    public List<StationData> getAll() {
        return stationDataDao.getAll();
    }

    @Override
    public StationData getById(int companyId, String stationId) {
        StationDataKey key = new StationDataKey();
        key.setCompanyId(companyId);
        key.setStationId(stationId);
        return stationDataDao.get(key);
    }

    @Override
    public void update(StationData stationData) {
        stationDataDao.update(stationData);
    }

    @Override
    public void saveOrUpdate(StationData stationData) {
        StationDataKey key = new StationDataKey();
        key.setCompanyId(stationData.getCompanyId());
        key.setStationId(stationData.getStationId());
        StationData stationData1 = stationDataDao.get(key);
        if (stationData1 == null) {
            stationDataDao.save(stationData);
        } else {
            BeanUtils.copyProperties(stationData1, stationData, true);//这里把null的忽略掉
            stationDataDao.update(stationData);
        }
    }
}
