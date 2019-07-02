package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.StandDataDao;
import com.xhkj.server.energy.dao.mybatis.vo.StandData;
import com.xhkj.server.energy.dao.mybatis.vo.StandDataKey;
import com.xhkj.server.energy.service.StandDataService;
import com.xhkj.server.energy.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandDataServiceImpl implements StandDataService {

    @Autowired
    private StandDataDao standDataDao;

    @Override
    public List<StandData> getAll() {
        return standDataDao.getAll();
    }

    @Override
    public StandData getById(int companyId, String standId) {
        StandDataKey key = new StandDataKey();
        key.setCompanyId(companyId);
        key.setStandId(standId);
        return standDataDao.get(key);
    }

    @Override
    public void update(StandData standData) {
        standDataDao.update(standData);
    }

    @Override
    public void saveOrUpdate(StandData standData) {
        StandDataKey key = new StandDataKey();
        key.setCompanyId(standData.getCompanyId());
        key.setStandId(standData.getStandId());
        StandData standData1 = standDataDao.get(key);
        if (standData1 == null) {
            standData.setDesignArea(0);
            standData.setRealArea(0);
            standDataDao.save(standData);
        } else {
            standData.setDesignArea(standData1.getDesignArea()==null?0:standData1.getDesignArea());
            standData.setRealArea(standData1.getRealArea()==null?0:standData1.getRealArea());
            standDataDao.update(standData);
        }
    }
}
