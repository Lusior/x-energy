package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.MenuDao;
import com.xhkj.server.energy.dao.mybatis.mapper.MenuMapper;
import com.xhkj.server.energy.dao.mybatis.vo.Menu;
import com.xhkj.server.energy.dao.mybatis.vo.MenuExample;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class MenuDaoImpl implements MenuDao {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public void save(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public Menu get(Integer menuId) {
        return menuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public void update(Menu menu) {
        menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public void delete(Integer menuId) {
        menuMapper.deleteByPrimaryKey(menuId);
    }

    ////*******自定义开始********//

    @Override
    public List<Menu> getAll() {
        MenuExample example = new MenuExample();
        example.setOrderByClause("MENU_ID");
        return menuMapper.selectByExample(example);
    }

    @Override
    public List<Menu> getMenusByRoles(String[] ids) {
        return menuMapper.getMenusByRoles(ids);
    }

    @Override
    public List<Menu> getAllWithItemListByRoleIds(String[] ids) {
        return menuMapper.getAllWithItemListByRoleIds(ids);
    }

    //**********自定义结束*****////

}
