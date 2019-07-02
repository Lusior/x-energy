package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.StationDataDao;
import com.xhkj.server.energy.dao.mybatis.mapper.StationDataMapper;
import com.xhkj.server.energy.dao.mybatis.vo.StationData;
import com.xhkj.server.energy.dao.mybatis.vo.StationDataExample;
import com.xhkj.server.energy.dao.mybatis.vo.StationDataKey;
import com.xhkj.server.energy.shiro.ShiroUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class StationDataDaoImpl implements StationDataDao {

    @Resource
    private StationDataMapper stationDataMapper;

    @Override
    public void save(StationData stationData) {
        stationDataMapper.insert(stationData);
    }

    @Override
    public StationData get(StationDataKey stationDataKey) {
        return stationDataMapper.selectByPrimaryKey(stationDataKey);
    }

    @Override
    public void update(StationData stationData) {
        stationDataMapper.updateByPrimaryKey(stationData);
    }

    @Override
    public void delete(StationDataKey stationDataKey) {
        stationDataMapper.deleteByPrimaryKey(stationDataKey);
    }

    ////*******自定义开始********//

    @Override
    public List<StationData> getAll() {
        StationDataExample example = new StationDataExample();
        example.createCriteria().andCompanyIdEqualTo(ShiroUtil.getCompanyId());
        example.setOrderByClause("MID(STATION_ID,2,2)+1");
        return stationDataMapper.selectByExample(example);
    }

    //这个用在预测数据自动任务里
    @Override
    public List<StationData> getStationListByCompanyID(Integer companyID) {
        StationDataExample example = new StationDataExample();
        example.createCriteria().andCompanyIdEqualTo(companyID);
        return stationDataMapper.selectByExample(example);
    }

    //**********自定义结束*****////

}
