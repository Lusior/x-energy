package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.MenuItem;

import java.util.List;
import java.util.Set;

public interface MenuItemDao extends Dao<MenuItem, Integer> {

    ////*******自定义开始********//
    List<MenuItem> getItemByRoleId(String roleId);

    Set<String> getPermissionsByRoleIds(String[] ids);
    //**********自定义结束*****////

}
