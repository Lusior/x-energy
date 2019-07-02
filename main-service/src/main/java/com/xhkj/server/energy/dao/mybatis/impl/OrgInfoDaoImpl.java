package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.OrgInfoDao;
import com.xhkj.server.energy.dao.mybatis.mapper.OrgInfoMapper;
import com.xhkj.server.energy.dao.mybatis.vo.OrgInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class OrgInfoDaoImpl implements OrgInfoDao {

    @Resource
    private OrgInfoMapper orgInfoMapper;

    @Override
    public void save(OrgInfo orgInfo) {
        orgInfoMapper.insert(orgInfo);
    }

    @Override
    public OrgInfo get(Integer orgId) {
        return orgInfoMapper.selectByPrimaryKey(orgId);
    }

    @Override
    public void update(OrgInfo orgInfo) {
        orgInfoMapper.updateByPrimaryKey(orgInfo);
    }

    @Override
    public void delete(Integer orgId) {
        orgInfoMapper.deleteByPrimaryKey(orgId);
    }

    ////*******自定义开始********//
    //**********自定义结束*****////

}
