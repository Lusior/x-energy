package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.RoleDao;
import com.xhkj.server.energy.dao.mybatis.mapper.RoleMapper;
import com.xhkj.server.energy.dao.mybatis.vo.Role;
import com.xhkj.server.energy.page.BootstrapTableParams;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public void save(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public Role get(Integer roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public void update(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void delete(Integer roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }

    ////*******自定义开始********//
    @Override
    public List<Role> getAll(BootstrapTableParams params) {
        return roleMapper.getAll(params);
    }

    @Override
    public Role getById(String roleId) {
        return roleMapper.getById(roleId);
    }

    @Override
    public void deleteRoleItemRelByRoleId(String roleId) {
        roleMapper.deleteRoleItemRelByRoleId(roleId);
    }

    @Override
    public Integer insertRoleItemRel(List<Map<String, Object>> list) {
        return roleMapper.insertRoleItemRel(list);
    }

    @Override
    public Integer deleteByRoleId(String roleId) {
        return roleMapper.deleteByRoleId(roleId);
    }

    @Override
    public void deleteRoleOprRelByRoleId(String roleId) {
        roleMapper.deleteRoleOprRelByRoleId(roleId);
    }

    @Override
    public List<Role> getRoleByOprId(String oprId) {
        return roleMapper.getRoleByOprId(oprId);
    }
    //**********自定义结束*****////

}
