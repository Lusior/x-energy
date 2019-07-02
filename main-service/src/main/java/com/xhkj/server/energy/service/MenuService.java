package com.xhkj.server.energy.service;

import com.xhkj.server.energy.dao.mybatis.vo.Menu;
import com.xhkj.server.energy.entity.MenuPage;

import java.util.List;
import java.util.Set;

public interface MenuService {
    List<Menu> getAll();

    List<Menu> getMenus(Set<String> roles);

    List<MenuPage> getMenuWithItemList(String roleId);

    List<MenuPage> getMenuWithItemList(Set<String> rolesIds);
}
