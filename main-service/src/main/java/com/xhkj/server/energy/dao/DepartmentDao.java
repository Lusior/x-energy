package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.Department;
import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.page.BootstrapTableParams;

import java.util.List;

public interface DepartmentDao extends Dao<Department, Integer> {

    ////*******自定义开始********//
    Department findDepId(int id);

    Integer existDepName(String depName);

    List<OprInfo> detailDepId(int depId);

    int find0prDepId(int depId);

    List<Department> getAll(BootstrapTableParams params);
    //**********自定义结束*****////

}
