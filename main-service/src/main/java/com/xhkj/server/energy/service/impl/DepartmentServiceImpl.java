package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.DepartmentDao;
import com.xhkj.server.energy.dao.mybatis.mapper.DepartmentMapper;
import com.xhkj.server.energy.dao.mybatis.vo.Department;
import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.page.BootstrapTableParams;
import com.xhkj.server.energy.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentDao departmentDao;
    //TODO dao插入没有返回值，暂用mapper
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public Department findDepId(int id) {
        return departmentDao.findDepId(id);
    }

    /**
     * 验证部门名称
     */
    @Override
    public Integer existDepName(String depName) {
        return departmentDao.existDepName(depName);
    }

    @Override
    public int insertDept(Department dept) {
        return departmentMapper.insertSelective(dept);
    }

    @Override
    public int updateDept(Department department) {
        return departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public int deleteDept(int id) {
        return departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<OprInfo> detailDepId(int depId) {
        List<OprInfo> list = departmentDao.detailDepId(depId);
        return list;
    }

    @Override
    public int find0prDepId(int depId) {
        return departmentDao.find0prDepId(depId);
    }

    @Override
    public List<Department> getAll(BootstrapTableParams params) {
        return departmentDao.getAll(params);
    }
}
