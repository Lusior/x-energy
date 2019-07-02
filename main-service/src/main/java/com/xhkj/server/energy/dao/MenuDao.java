package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.Menu;

import java.util.List;

public interface MenuDao extends Dao<Menu, Integer> {

    ////*******自定义开始********//
    List<Menu> getAll();

    List<Menu> getMenusByRoles(String[] ids);

    List<Menu> getAllWithItemListByRoleIds(String[] ids);
    //**********自定义结束*****////

}
