package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.MenuItemDao;
import com.xhkj.server.energy.dao.mybatis.mapper.MenuItemMapper;
import com.xhkj.server.energy.dao.mybatis.vo.MenuItem;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Repository
public class MenuItemDaoImpl implements MenuItemDao {

    @Resource
    private MenuItemMapper menuItemMapper;

    @Override
    public void save(MenuItem menuItem) {
        menuItemMapper.insert(menuItem);
    }

    @Override
    public MenuItem get(Integer itemId) {
        return menuItemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public void update(MenuItem menuItem) {
        menuItemMapper.updateByPrimaryKey(menuItem);
    }

    @Override
    public void delete(Integer itemId) {
        menuItemMapper.deleteByPrimaryKey(itemId);
    }


    ////*******自定义开始********//
    @Override
    public List<MenuItem> getItemByRoleId(String roleId) {
        return menuItemMapper.getItemByRoleId(roleId);
    }

    @Override
    public Set<String> getPermissionsByRoleIds(String[] ids) {
        return menuItemMapper.getPermissionsByRoleIds(ids);
    }
    //**********自定义结束*****////

}
