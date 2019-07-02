package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.StandDataDao;
import com.xhkj.server.energy.dao.mybatis.mapper.StandDataMapper;
import com.xhkj.server.energy.dao.mybatis.vo.StandData;
import com.xhkj.server.energy.dao.mybatis.vo.StandDataExample;
import com.xhkj.server.energy.dao.mybatis.vo.StandDataKey;
import com.xhkj.server.energy.shiro.ShiroUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class StandDataDaoImpl implements StandDataDao {

    @Resource
    private StandDataMapper standDataMapper;

    @Override
    public void save(StandData standData) {
        standDataMapper.insert(standData);
    }

    @Override
    public StandData get(StandDataKey standDataKey) {
        return standDataMapper.selectByPrimaryKey(standDataKey);
    }

    @Override
    public void update(StandData standData) {
        standDataMapper.updateByPrimaryKey(standData);
    }

    @Override
    public void delete(StandDataKey standDataKey) {
        standDataMapper.deleteByPrimaryKey(standDataKey);
    }

    ////*******自定义开始********//

    @Override
    public List<StandData> getAll() {
        StandDataExample example = new StandDataExample();
        example.createCriteria().andCompanyIdEqualTo(ShiroUtil.getCompanyId());
        example.setOrderByClause("MID(STATION_ID,2,2)+1,MID(STAND_ID,2,2)");
        return standDataMapper.selectByExample(example);
    }

    @Override
    public List<StandData> getStandListByCompanyId(Integer componyId) {
        StandDataExample example = new StandDataExample();
        example.createCriteria().andCompanyIdEqualTo(componyId);
        return standDataMapper.selectByExample(example);
    }

    //**********自定义结束*****////

}
