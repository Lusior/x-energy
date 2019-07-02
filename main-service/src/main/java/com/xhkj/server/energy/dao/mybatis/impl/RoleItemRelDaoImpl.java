package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.RoleItemRelDao;
import com.xhkj.server.energy.dao.mybatis.mapper.RoleItemRelMapper;
import com.xhkj.server.energy.dao.mybatis.vo.RoleItemRel;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class RoleItemRelDaoImpl implements RoleItemRelDao {

    @Resource
    private RoleItemRelMapper roleItemRelMapper;

    @Override
    public void save(RoleItemRel roleItemRel) {
        roleItemRelMapper.insert(roleItemRel);
    }

    @Override
    public RoleItemRel get(Integer id) {
        return roleItemRelMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(RoleItemRel roleItemRel) {
        roleItemRelMapper.updateByPrimaryKey(roleItemRel);
    }

    @Override
    public void delete(Integer id) {
        roleItemRelMapper.deleteByPrimaryKey(id);
    }

    ////*******自定义开始********//
    //**********自定义结束*****////

}
