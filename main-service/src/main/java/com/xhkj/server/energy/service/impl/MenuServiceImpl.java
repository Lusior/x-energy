package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.ConstantsBase;
import com.xhkj.server.energy.dao.MenuDao;
import com.xhkj.server.energy.dao.MenuItemDao;
import com.xhkj.server.energy.dao.mybatis.vo.Menu;
import com.xhkj.server.energy.dao.mybatis.vo.MenuItem;
import com.xhkj.server.energy.entity.MenuPage;
import com.xhkj.server.energy.service.MenuService;
import com.xhkj.server.energy.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private MenuItemDao menuItemDao;

    @Override
    public List<Menu> getAll() {
        return menuDao.getAll();
    }

    @Override
    public List<Menu> getMenus(Set<String> rolesIds) {
        // 需要递归所有的菜单
        List<Menu> menusAll = getAll();
        String[] ids = new String[rolesIds.size()];
        int i = 0;
        for (String id : rolesIds) {
            ids[i++] = id;
        }
        /* 能获取到当前角色对应的菜单，菜单的上级菜单获取不到 */
        List<Menu> menus = menuDao.getMenusByRoles(ids);
        List<Menu> retList = new ArrayList<Menu>();
        for (i = 0; i < menus.size(); i++) {
            Menu menu = menus.get(i);
            addMenuToSet(menu.getMenuId() + "", menusAll, retList);
        }
        // 去重
        List<Menu> result = new ArrayList<Menu>();
        for (Menu menu : retList) {
            if (!result.contains(menu)) {
                result.add(menu);
            }
        }
        //根据menu_id排序
        Collections.sort(result);
        return result;
    }

    /**
     * 根据角色获取菜单以及权限列表
     *
     * @param @param  roleId
     * @param @return 设定文件
     * @return List<MenuPage> 返回类型
     * @throws
     * @Title: getRoleMenuWithItemList
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    @Override
    public List<MenuPage> getMenuWithItemList(String roleId) {
        /* 菜单表所有的菜单*/
        List<Menu> menusAll = getMenus();
        /* 能获取到当前角色对应的菜单，菜单的上级菜单获取不到 */
        List<Menu> menus = menuDao.getMenusByRoles(StringUtils.isEmpty(roleId) ? null : new String[]{roleId});
        /* 递归后返回的菜单list */
        List<Menu> retList = new ArrayList<Menu>();
        // 循环当前角色对应的菜单
        for (Menu menu : menus) {
            // 递归寻找父菜单
            addMenuToSet(menu.getMenuId() + "", menusAll, retList);
        }
        // 去重后最终返回的菜单list
        List<Menu> resultMenuList = new ArrayList<Menu>();
        // 去重
        for (Menu menu : retList) {
            if (!resultMenuList.contains(menu)) {
                resultMenuList.add(menu);
            }
        }
        // 查询当前角色的权限对象list
        List<MenuItem> itemIds = menuItemDao.getItemByRoleId(roleId);
        // 返回页面的menuPages
        List<MenuPage> menuPages = new ArrayList<MenuPage>();
        /* 组合当前菜单与菜单项的MenuPage */
        for (Menu retsultMenu : resultMenuList) {
            MenuPage menuPage = new MenuPage();
            menuPage.setId(retsultMenu.getMenuId() + "");
            menuPage.setCode(retsultMenu.getMenuCode());
            menuPage.setPid(retsultMenu.getMenuPid());
            menuPage.setText(retsultMenu.getMenuName());
            menuPage.setUrl(retsultMenu.getMenuUrl());
            menuPages.add(menuPage);
        }
        for (MenuItem item : itemIds) {
            MenuPage itemPage = new MenuPage();
            itemPage.setId(item.getItemId() + "");
            itemPage.setPid(item.getMenuId() + "");
            itemPage.setText(item.getItemName());
            itemPage.setUrl("");
            // 设置为叶节点
            itemPage.setIsLeaf(ConstantsBase.MENU_LEAF_NODE);
            menuPages.add(itemPage);
        }
        return menuPages;
    }

    /**
     * 根据角色组获取菜单以及权限列表
     *
     * @param
     * @return
     */
    public List<MenuPage> getMenuWithItemList(Set<String> rolesIds) {

        String[] ids = new String[rolesIds.size()];
        int i = 0;
        for (String id : rolesIds) {
            ids[i++] = id;
        }
        List<Menu> menus = menuDao.getAllWithItemListByRoleIds(ids);
        List<MenuPage> menuPages = new ArrayList<MenuPage>();
        for (Menu menu : menus) {
            MenuPage menuPage = new MenuPage();
            menuPage.setId(menu.getMenuId() + "");
            menuPage.setCode(menu.getMenuCode());
            menuPage.setPid(menu.getMenuPid());
            menuPage.setText(menu.getMenuName());
            menuPage.setUrl(menu.getMenuUrl());
            menuPages.add(menuPage);
            for (MenuItem item : menu.getMenuItemList()) {
                MenuPage itemPage = new MenuPage();
                itemPage.setId(item.getItemId() + "");
                menuPage.setCode(menu.getMenuCode());
                itemPage.setPid(item.getMenuId() + "");
                itemPage.setText(item.getItemName());
                // 设置为叶节点
                itemPage.setIsLeaf(ConstantsBase.MENU_LEAF_NODE);
                menuPages.add(itemPage);
            }
        }
        return menuPages;

    }

    /**
     * 获取全部菜单
     *
     * @return void 返回类型
     */
    public List<Menu> getMenus() {
        return menuDao.getAll();
    }

    /**
     * @param @param  pid
     * @param @param  menusAll
     * @param @param  retMenu
     * @param @return 设定文件
     * @return Menu 返回类型
     * @Title: addMenuToSet
     * @Description: TODO(递归添加menu到set)
     */
    private Menu addMenuToSet(String pid, List<Menu> menusAll, List<Menu> retList) {
        for (Menu m : menusAll) {
            if (m.getMenuId().toString().equals(pid)) {
                retList.add(m);
                pid = m.getMenuPid();
                break;
            }
        }
        // 是否为顶层菜单
        if (ConstantsBase.MENU_PID_HIGHEST.equals(pid)) {
            return null;
        }
        return addMenuToSet(pid, menusAll, retList);
    }
}
