package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.CollectDataDao;
import com.xhkj.server.energy.dao.mybatis.vo.CollectData;
import com.xhkj.server.energy.service.CollectDataService;
import com.xhkj.server.energy.shiro.ShiroUtil;
import com.xhkj.server.energy.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollectDataServiceImpl implements CollectDataService {

    @Autowired
    private CollectDataDao collectDataDao;

    //map 里面groupType是1就是按station_id 分组, 值为2，按stand_id分组,
    @Override
    public List<CollectData> getETimeCollectDataList(String endTime, int groupType) {
        return collectDataDao.getETimeCollectDataList(endTime, groupType);
    }

    @Override
    public List<CollectData> getByTimeRangeAndStandId(Date startTime, Date endTime, String standId) {
        return collectDataDao.getByTimeRangeAndStandId(startTime, endTime, standId);
    }

    @Override
    public void save(CollectData collectData) {
        collectDataDao.save(collectData);
    }

    @Override
    public Integer getMaxBatchNumber() {
        return collectDataDao.getMaxBatchNumber(null, ShiroUtil.getCompanyId());
    }

    @Override
    public List<Map<String, Object>> getStationRtNwData(Integer maxBatchNumber) {
        return collectDataDao.getStationRtNwData(maxBatchNumber);
    }
}
