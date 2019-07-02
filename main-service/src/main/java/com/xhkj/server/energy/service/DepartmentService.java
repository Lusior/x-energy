package com.xhkj.server.energy.service;

import com.xhkj.server.energy.dao.mybatis.vo.Department;
import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.page.BootstrapTableParams;

import java.util.List;

public interface DepartmentService {
    Department findDepId(int id);

    Integer existDepName(String depName);

    int insertDept(Department dept);

    int updateDept(Department department);

    int deleteDept(int id);

    List<OprInfo> detailDepId(int parseInt);

    int find0prDepId(int parseInt);

    List<Department> getAll(BootstrapTableParams params);
}
