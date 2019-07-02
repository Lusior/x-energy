package com.xhkj.server.energy.controller;

import com.github.pagehelper.PageHelper;
import com.xhkj.server.energy.dao.mybatis.vo.Role;
import com.xhkj.server.energy.entity.MenuPage;
import com.xhkj.server.energy.page.BootstrapTablePage;
import com.xhkj.server.energy.page.BootstrapTableParams;
import com.xhkj.server.energy.service.MenuService;
import com.xhkj.server.energy.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author A18ccms a18ccms_gmail_com
 * @ClassName: RoleController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2015年9月8日 下午8:56:26
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @RequestMapping("/index")
    public String index(HttpSession session) {
        pageInitToSession(session);
        return "sys/role";
    }

    @SuppressWarnings("unchecked")
    @RequiresPermissions("role:search")
    @RequestMapping("/list")
    @ResponseBody
    public BootstrapTablePage<Role> list(BootstrapTableParams params) {
        initParams(params);
        PageHelper.startPage(params.getPageNum(), params.getLimit());
        List<Role> list = roleService.getAll(params);
        return new BootstrapTablePage<>(list);
    }

    @RequiresPermissions("role:detail")
    @RequestMapping("/detail/{roleId}")
    @ResponseBody
    public Map<String, Object> detail(@PathVariable("roleId") String roleId) {
        Role role = roleService.getById(roleId);
        //获取当前角色的所有权限
        List<MenuPage> menupages = menuService.getMenuWithItemList(roleId);
        List<Map<String, Object>> pageList = new ArrayList<Map<String, Object>>();
        //组装页面数据
        for (MenuPage menuPage : menupages) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", menuPage.getId());
            map.put("pId", menuPage.getPid());
            map.put("name", menuPage.getText());
            map.put("isParent", menuPage.getIsLeaf() == null ? true : false);
            if ((menuPage.getId().equals("1000") || menuPage.getPid().equals("1000")) && !menuPage.getId().equals("1001")) {
                map.put("open", true);
            }
            map.put("checked", false);
            map.put("chkDisabled", false);
            pageList.add(map);
        }
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("role", role);
        retMap.put("treeData", pageList);
        return retMap;
    }

    @RequestMapping("/item")
    @ResponseBody
    public List<Map<String, Object>> getItem() {
        //获取全部角色
        BootstrapTableParams params = new BootstrapTableParams();
        params.setOffset(0);
        params.setLimit(100);
        List<Role> allRolesList = roleService.getAll(params);
        Set<String> roles = new HashSet<String>();
        for (int i = 0; i < allRolesList.size(); i++) {
            roles.add(String.valueOf(allRolesList.get(i).getRoleId()));
        }
        List<Map<String, Object>> pageList = new ArrayList<Map<String, Object>>();
        //获取当前角色的所有权限
        if (roles != null) {
            List<MenuPage> menupages = menuService.getMenuWithItemList(roles);
            for (MenuPage menuPage : menupages) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", menuPage.getId());
                map.put("pId", menuPage.getPid());
                map.put("name", menuPage.getText());
                map.put("isParent", menuPage.getIsLeaf() == null ? true : false);
                if ((menuPage.getId().equals("1000") || menuPage.getPid().equals("1000")) && !menuPage.getId().equals("1001")) {
                    map.put("open", true);
                }
                map.put("checked", false);
                map.put("chkDisabled", false);
                pageList.add(map);
            }
        }
        return pageList;
    }

    @SuppressWarnings("unchecked")
    @RequiresPermissions("role:add")
    @RequestMapping("/adddo")
    @ResponseBody
    public Boolean add_do(HttpServletRequest request, HttpServletResponse response, Role role, String rolesStr) {
        if (role == null || StringUtils.isEmpty(role.getRoleName()) || StringUtils.isEmpty(rolesStr)) {
            return false;
        }
        // 添加更创建更新人信息
        initEntity(role);
        role.setCrtId(String.valueOf(this.getCurrOprInfo().getOprId()));
        String[] items = rolesStr.split(",");
        return roleService.saveRoleAndItem(role, items);
    }

    @RequiresPermissions("role:del")
    @RequestMapping("/delete/{roleId}")
    @ResponseBody
    public Boolean delete(@PathVariable("roleId") String roleId) {
        if (StringUtils.isNotEmpty(roleId)) {
            return roleService.deleteRole(roleId);
        }
        return false;
    }

    @RequiresPermissions("role:update")
    @RequestMapping("/modify/{roleId}")
    @ResponseBody
    public Map<String, Object> modify(HttpServletRequest request, HttpServletResponse response, @PathVariable("roleId") String roleId) {
        Role role = roleService.getById(roleId);
        List<Map<String, Object>> pageList = new ArrayList<Map<String, Object>>();
        //获取当前角色的权限
        List<MenuPage> byRole = menuService.getMenuWithItemList(roleId);
        //获取所有权限(以超级管理员为标准的id1000)
        List<MenuPage> byRoles = menuService.getMenuWithItemList("1000");
        //进行对比
        for (MenuPage menuPage1 : byRoles) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", menuPage1.getId());
            map.put("pId", menuPage1.getPid());
            map.put("name", menuPage1.getText());
            map.put("isParent", menuPage1.getIsLeaf() == null ? true : false);
            if ((menuPage1.getId().equals("1000") || menuPage1.getPid().equals("1000")) /*&& !menuPage1.getId().equals( "1001")*/) {
                map.put("open", true);
            }

            map.put("checked", false);
            for (MenuPage menuPage2 : byRole) {
                //变成已勾选
                if (menuPage1.getId().equals(menuPage2.getId())) {
                    map.put("checked", true);
                }
            }
            pageList.add(map);
        }
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("role", role);
        retMap.put("treeData", pageList);
        return retMap;

    }

    @SuppressWarnings("unchecked")
    @RequiresPermissions("role:update")
    @RequestMapping("/modifydo")
    @ResponseBody
    public Boolean modify_do(HttpServletRequest request, HttpServletResponse response, Role role, String rolesStr) {
        if (role == null || role.getRoleId() == null || StringUtils.isEmpty(role.getRoleName()) || StringUtils.isEmpty(rolesStr)) {
            return false;
        }
        // 添加更创建更新人信息
        initUpt(role);
        role.setUptId(String.valueOf(this.getCurrOprInfo().getOprId()));
        String[] items = rolesStr.split(",");
        return roleService.modifyRoleAndItem(role, items);
    }
}
