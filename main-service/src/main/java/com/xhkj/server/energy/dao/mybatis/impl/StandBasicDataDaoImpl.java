package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.StandBasicDataDao;
import com.xhkj.server.energy.dao.mybatis.mapper.StandBasicDataMapper;
import com.xhkj.server.energy.dao.mybatis.vo.StandBasicData;
import com.xhkj.server.energy.dao.mybatis.vo.StandBasicDataExample;
import com.xhkj.server.energy.dao.mybatis.vo.StandBasicDataKey;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class StandBasicDataDaoImpl implements StandBasicDataDao {

    @Resource
    private StandBasicDataMapper standBasicDataMapper;

    @Override
    public void save(StandBasicData standBasicData) {
        standBasicDataMapper.insert(standBasicData);
    }

    @Override
    public StandBasicData get(StandBasicDataKey standBasicDataKey) {
        return standBasicDataMapper.selectByPrimaryKey(standBasicDataKey);
    }

    @Override
    public void update(StandBasicData standBasicData) {
        standBasicDataMapper.updateByPrimaryKey(standBasicData);
    }

    @Override
    public void delete(StandBasicDataKey standBasicDataKey) {
        standBasicDataMapper.deleteByPrimaryKey(standBasicDataKey);
    }

    ////*******自定义开始********//

    @Override
    public List<StandBasicData> getByCompany(int company) {
        StandBasicDataExample example = new StandBasicDataExample();
        example.createCriteria().andCompanyIdEqualTo(company);
        return standBasicDataMapper.selectByExample(example);
    }

    //**********自定义结束*****////

}
