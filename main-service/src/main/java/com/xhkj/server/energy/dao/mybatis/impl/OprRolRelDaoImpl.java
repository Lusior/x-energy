package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.OprRolRelDao;
import com.xhkj.server.energy.dao.mybatis.mapper.OprRolRelMapper;
import com.xhkj.server.energy.dao.mybatis.vo.OprRolRel;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class OprRolRelDaoImpl implements OprRolRelDao {

    @Resource
    private OprRolRelMapper oprRolRelMapper;

    @Override
    public void save(OprRolRel oprRolRel) {
        oprRolRelMapper.insert(oprRolRel);
    }

    @Override
    public OprRolRel get(Integer id) {
        return oprRolRelMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(OprRolRel oprRolRel) {
        oprRolRelMapper.updateByPrimaryKey(oprRolRel);
    }

    @Override
    public void delete(Integer id) {
        oprRolRelMapper.deleteByPrimaryKey(id);
    }

    ////*******自定义开始********//
    //**********自定义结束*****////

}
