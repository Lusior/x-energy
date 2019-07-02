package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.DepartmentDao;
import com.xhkj.server.energy.dao.mybatis.mapper.DepartmentMapper;
import com.xhkj.server.energy.dao.mybatis.vo.Department;
import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.page.BootstrapTableParams;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public void save(Department department) {
        departmentMapper.insert(department);
    }

    @Override
    public Department get(Integer depId) {
        return departmentMapper.selectByPrimaryKey(depId);
    }

    @Override
    public void update(Department department) {
        departmentMapper.updateByPrimaryKey(department);
    }

    @Override
    public void delete(Integer depId) {
        departmentMapper.deleteByPrimaryKey(depId);
    }


    ////*******自定义开始********//
    @Override
    public Department findDepId(int id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer existDepName(String depName) {
        return departmentMapper.existDepName(depName);
    }

    @Override
    public List<OprInfo> detailDepId(int depId) {
        return departmentMapper.detailDepId(depId);
    }

    @Override
    public int find0prDepId(int depId) {
        return departmentMapper.find0prDepId(depId);
    }

    @Override
    public List<Department> getAll(BootstrapTableParams params) {
        return departmentMapper.getAll(params);
    }
    //**********自定义结束*****////

}
