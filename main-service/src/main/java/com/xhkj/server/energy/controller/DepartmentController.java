package com.xhkj.server.energy.controller;

import com.xhkj.server.energy.dao.mybatis.vo.Department;
import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.entity.DetailOprInfo;
import com.xhkj.server.energy.page.BootstrapTablePage;
import com.xhkj.server.energy.page.BootstrapTableParams;
import com.xhkj.server.energy.page.Page;
import com.xhkj.server.energy.service.DepartmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "sys/dep")
public class DepartmentController extends BaseController<Department> {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/index")
    public String index(HttpSession session) {
        pageInitToSession(session);
        return "sys/dep";
    }

    /**
     * 部门初始化查询
     */
    @RequiresPermissions("dep:search")
    @RequestMapping("/list")
    @ResponseBody
    public BootstrapTablePage<Department> list(BootstrapTableParams params, String depName) {
        initParams(params);
        Map<String, String> map = new HashMap<>();
        map.put("depName", depName);
        params.setParams(map);
        List<Department> all = departmentService.getAll(params);
        return new BootstrapTablePage<>(all);
    }

    /**
     * 根据ID查询数据
     */
    @RequiresPermissions("dep:search")
    @RequestMapping("/findDepId")
    @ResponseBody
    public Department findDepId(String depId) {
        int id = Integer.parseInt(depId);
        return departmentService.findDepId(id);
    }

    /**
     * 验证部门名称是否存在
     */
    @RequiresPermissions("dep:search")
    @RequestMapping("/existDepName")
    @ResponseBody
    public boolean existDepName(HttpServletRequest request, String depName, HttpServletResponse response) throws Exception {
        return departmentService.existDepName(depName) > 0;
    }

    /**
     * 添加部门
     */
    @RequiresPermissions("dep:search")
    @RequestMapping("/insertDept")
    @ResponseBody
    public Map<String, Object> insertDept(HttpServletRequest request, Department department, HttpServletResponse response)
            throws Exception {
        int OprId = getCurrOprInfo().getOprId();
        Department dept = new Department();
        dept.setDepName(department.getDepName());
        dept.setDepRmk(department.getDepRmk());
        dept.setDepSts(department.getDepSts());
        dept.setCrtId(String.valueOf(OprId));
        dept.setUptId(String.valueOf(OprId));
        Map<String, Object> map1 = new HashMap<String, Object>();
        int count = departmentService.insertDept(dept);
        if (count == 1) {
            map1.put("msg", true);
        } else {
            map1.put("msg", false);
        }
        return map1;
    }

    /**
     * 修改部门
     */
    @RequiresPermissions("dep:search")
    @RequestMapping("/updateDept")
    @ResponseBody
    public Map<String, Object> updateDept(HttpServletRequest request,
                                          Department department, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map1 = new HashMap<String, Object>();
        int count = departmentService.updateDept(department);
        if (count == 1) {
            map1.put("msg", true);
        } else {
            map1.put("msg", false);
        }
        return map1;
    }

    /**
     * 删除部门
     */
    @RequiresPermissions("dep:search")
    @RequestMapping("/deleteDept")
    @ResponseBody
    public Map<String, Object> deleteDept(HttpServletRequest request,
                                          String depId, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(depId);
        Map<String, Object> map1 = new HashMap<String, Object>();
        int count = departmentService.deleteDept(id);
        if (count == 1) {
            map1.put("msg", true);
        } else {
            map1.put("msg", false);
        }
        return map1;
    }

    /**
     * 部门详情
     */
    @RequiresPermissions("dep:search")
    @RequestMapping("/detailDepId")
    @ResponseBody
    public Page<DetailOprInfo> detailDepId(HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        String depId = request.getParameter("depId");
        Page<DetailOprInfo> page = new Page<DetailOprInfo>();
        int OprId = getCurrOprInfo().getOprId();
        // OprInfo oprInfo;
        if (depId != null) {
            List<OprInfo> list = departmentService.detailDepId(Integer
                    .parseInt(depId));
            List<DetailOprInfo> detaillist = new ArrayList<DetailOprInfo>();
            DetailOprInfo detailOprInfo = new DetailOprInfo();
            for (int i = 0; i < list.size(); i++) {
                // oprInfo = list.get(i);
                detailOprInfo.setOprInfo(list.get(i));
                detailOprInfo.setLogonID(OprId);
                detaillist.add(detailOprInfo);
            }
            page.setRows(detaillist);
            page.setTotal(detaillist.size());
            return page;
        } else {
            return null;
        }
    }

    /**
     * 模糊搜索部门
     */
    // @RequiresPermissions("dep:search")
    // @RequestMapping("/searchDept")
    // @ResponseBody
    // public List<Department> searchDept(HttpServletRequest request,
    // //@RequestBody String depName,
    // String depName,
    // HttpServletResponse response) throws Exception {
    // //String a = request.getParameter("depName");
    // System.out.print("ccc   "+depName+ "  ");
    // List<Department> list = departmentService.searchDept(depName);
    // return list;
    // }

    /**
     * 部门操作员校验
     */
    @RequiresPermissions("dep:search")
    @RequestMapping("/find0prDepId")
    @ResponseBody
    public Map<String, Object> find0prDepId(HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String depId = request.getParameter("depId");
        int count = departmentService.find0prDepId(Integer.parseInt(depId));
        if (count > 0) {
            map.put("msg", false);
        } else {
            map.put("msg", true);
        }
        return map;
    }

}