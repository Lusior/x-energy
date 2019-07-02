package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.Role;
import com.xhkj.server.energy.page.BootstrapTableParams;

import java.util.List;
import java.util.Map;

public interface RoleDao extends Dao<Role, Integer> {

    ////*******自定义开始********//
    List<Role> getAll(BootstrapTableParams params);

    Role getById(String roleId);

    void deleteRoleItemRelByRoleId(String roleId);

    Integer insertRoleItemRel(List<Map<String, Object>> list);

    Integer deleteByRoleId(String roleId);

    void deleteRoleOprRelByRoleId(String roleId);

    List<Role> getRoleByOprId(String oprId);
    //**********自定义结束*****////

}
