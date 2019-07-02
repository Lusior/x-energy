package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.CollectDataDao;
import com.xhkj.server.energy.dao.mybatis.mapper.CollectDataMapper;
import com.xhkj.server.energy.dao.mybatis.vo.CollectData;
import com.xhkj.server.energy.dao.mybatis.vo.CollectDataExample;
import com.xhkj.server.energy.shiro.ShiroUtil;
import com.xhkj.server.energy.utils.DateUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class CollectDataDaoImpl implements CollectDataDao {

    @Resource
    private CollectDataMapper collectDataMapper;

    @Override
    public void save(CollectData collectData) {
        collectDataMapper.insert(collectData);
    }

    @Override
    public CollectData get(Integer id) {
        return collectDataMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(CollectData collectData) {
        collectDataMapper.updateByPrimaryKey(collectData);
    }

    @Override
    public void delete(Integer id) {
        collectDataMapper.deleteByPrimaryKey(id);
    }

    ////*******自定义开始********//

    @Override
    public List<CollectData> getETimeCollectDataList(String endTime, int groupType) {
        Integer maxBatchNumber = getMaxBatchNumber(endTime, ShiroUtil.getCompanyId());
        CollectDataExample collectDataExample = new CollectDataExample();
        collectDataExample.createCriteria().andBatchNumberEqualTo(maxBatchNumber).andCompanyIdEqualTo(ShiroUtil.getCompanyId());
//        if (groupType == 1) {
//            collectDataExample.setOrderByClause("group by STATION_ID order by MID(STATION_ID,2,2)+1 ,MID(STAND_ID,2,2)");
//        } else {
//            collectDataExample.setOrderByClause("group by STAND_ID order by MID(STATION_ID,2,2)+1 ,MID(STAND_ID,2,2)");
//        }
        return collectDataMapper.selectByExample(collectDataExample);
    }

    @Override
    public List<CollectData> getByTimeRangeAndStandId(Date startTime, Date endTime, String standId) {
        CollectDataExample collectDataExample = new CollectDataExample();
        collectDataExample.createCriteria()
                .andOpTimeBetween(startTime, endTime)
                .andStandIdEqualTo(standId)
                .andCompanyIdEqualTo(ShiroUtil.getCompanyId());
        return collectDataMapper.selectByExample(collectDataExample);
    }

    @Override
    public List<CollectData> getDataByOpTimeFirst(String beginTime, String endTime, int companyId) {
        return collectDataMapper.getDataByOpTimeFirst(beginTime, endTime, companyId);
    }

    @Override
    public List<CollectData> getDataByOpTimeLast(String beginTime, String endTime, int companyId) {
        return collectDataMapper.getDataByOpTimeLast(beginTime, endTime, companyId);
    }

    @Override
    public Integer getMaxBatchNumber(String endTime, int companyId) {
        if (endTime == null) {
            endTime = DateUtils.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
        }
        return collectDataMapper.getMaxBatchNumber(endTime, companyId);
    }

    @Override
    public List<Map<String, Object>> getStationRtNwData(Integer maxBatchNumber) {
        return collectDataMapper.getStationRtNwData(maxBatchNumber);
    }

    //**********自定义结束*****////

}
