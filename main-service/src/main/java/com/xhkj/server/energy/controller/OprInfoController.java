package com.xhkj.server.energy.controller;

import com.github.pagehelper.PageHelper;
import com.xhkj.server.energy.dao.mybatis.vo.Department;
import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.dao.mybatis.vo.Role;
import com.xhkj.server.energy.page.BootstrapTablePage;
import com.xhkj.server.energy.page.BootstrapTableParams;
import com.xhkj.server.energy.service.DepartmentService;
import com.xhkj.server.energy.service.OprInfoService;
import com.xhkj.server.energy.service.RoleService;
import com.xhkj.server.energy.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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

@Slf4j
@Controller
@RequestMapping(value = "sys/oprinfo")
public class OprInfoController extends BaseController<OprInfo> {

    @Autowired
    private OprInfoService oprInfoService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    @RequiresPermissions("oprInfo:view")
    @RequestMapping("/index")
    public String index(HttpSession session) {
        pageInitToSession(session);
        return "sys/oprinfo";
    }

    @RequiresPermissions("oprInfo:search")
    @RequestMapping("/list")
    @ResponseBody
    public BootstrapTablePage<OprInfo> list(BootstrapTableParams params) {
        initParams(params);
        PageHelper.startPage(params.getPageNum(), params.getLimit());
        List<OprInfo> all= oprInfoService.getAll(params);
        return new BootstrapTablePage<>(all);
    }

    @RequiresPermissions("oprInfo:add")
    @RequestMapping("/adddo")
    @ResponseBody
    public Boolean add_do(String rolesStr, OprInfo oprInfo) {
        Set<String> roles = new HashSet<String>();
        if (StringUtils.isNotEmpty(rolesStr)) {
            CollectionUtils.addAll(roles, rolesStr.split(","));
        }
        this.initEntity(oprInfo);
        oprInfo.setRoles(roles);
        Boolean isOk = false;
        try {
            isOk = oprInfoService.save(oprInfo);
            log.info("添操作员成功,oprId:{}", this.getCurrOprInfo().getOprId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOk;
    }

    /*@RequiresPermissions("oprInfo:update")*/
    @RequestMapping("/modify/{oprId}")
    @ResponseBody
    public Map<String, Object> toModify(@PathVariable("oprId") String oprId) {
        //获取操作员信息
        OprInfo oprInfo = oprInfoService.getById(oprId);
        //获取该操作员角色列表
        List<Role> rolesList = roleService.getRoleByOprId(oprId);
        //获取全部角色 pageRole本来用在getAll方法
        BootstrapTableParams paramsRole = new BootstrapTableParams();
        paramsRole.setOffset(0);
        paramsRole.setLimit(100);
        List<Role> allRolesList = roleService.getAll(paramsRole);
        BootstrapTableParams paramsDep = new BootstrapTableParams();
        paramsDep.setOffset(0);
        paramsDep.setLimit(100);
        List<Department> depListAll = departmentService.getAll(paramsDep);
        List<Department> depList = new ArrayList<Department>();
        for (Department department : depListAll) {
            //查找选中项
            if (department.getDepId().equals(oprInfo.getDepId())) {
                depList.add(0, department);
            } else {
                depList.add(department);
            }
        }
        //创建返回数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rolesList", rolesList);
        map.put("allRolesList", allRolesList);
        map.put("oprInfo", oprInfo);
        map.put("depList", depList);
        return map;
    }

    @RequiresPermissions("oprInfo:update")
    @RequestMapping("/modifydo")
    @ResponseBody
    public Boolean modify_do(String rolesStr, OprInfo oprInfo) {
        Set<String> roles = new HashSet<String>();
        if (StringUtils.isNotEmpty(rolesStr)) {
            CollectionUtils.addAll(roles, rolesStr.split(","));
        }
        oprInfo.setRoles(roles);

        // 更新日期
        initUpt(oprInfo);
        // 更新的角色
        Boolean retCount = oprInfoService.modify(oprInfo);
        log.info("更新操作员完成：oprId:{}", this.getCurrOprInfo().getOprId());
        return retCount;
    }

    @RequiresPermissions("oprInfo:detail")
    @RequestMapping("/detail/{oprId}")
    @ResponseBody
    public Map<String, Object> detail(HttpServletRequest request, HttpServletResponse response, @PathVariable("oprId") String oprId) {
        // 获取修改的操作员的对象信息
        OprInfo oprInfo = oprInfoService.getById(oprId);
        //获取该操作员角色列表
        List<Role> rolesList = roleService.getRoleByOprId(oprId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rolesList", rolesList);
        map.put("oprInfo", oprInfo);
        // 获取
        return map;
    }

    @RequiresPermissions("oprInfo:del")
    @RequestMapping("/delete/{oprId}")
    @ResponseBody
    public Boolean del(@PathVariable("oprId") Integer oprId) {
        OprInfo oprInfo = new OprInfo();
        oprInfo.setOprId(oprId);
        this.initUpt(oprInfo);
        return oprInfoService.del(oprInfo);
    }

    @RequestMapping("/isExist")
    @ResponseBody
    public Boolean isExistOperInfo(String loginId) {
        return oprInfoService.isExistOprInfo(loginId);
    }

    @RequestMapping("/modifyPwddo")
    @ResponseBody
    public Boolean modifyPwddo(OprInfo oprInfo) {
        this.initUpt(oprInfo);
        oprInfo.setUptId(String.valueOf(this.getCurrOprInfo().getOprId()));
        Boolean ret = oprInfoService.modifyPwd(oprInfo);
        log.info("修改操作员密码完成：oprId:{}", this.getCurrOprInfo().getOprId());
        return ret;
    }

    /**
     * 首页密码修改
     *
     * @return
     */
    @RequestMapping("/up/passwd")
    @ResponseBody
    public Map<String, Object> upPassWd(String oldPassword, String newPassword) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            retMap.put("code", "01");
            retMap.put("message", "参数错误");
        } else if (oprInfoService.modifyPassword(oldPassword, newPassword)) {
            retMap.put("code", "00");
            retMap.put("message", "修改成功");
        } else {
            retMap.put("code", "02");
            retMap.put("message", "原密码错误");
        }
        return retMap;
    }

}
