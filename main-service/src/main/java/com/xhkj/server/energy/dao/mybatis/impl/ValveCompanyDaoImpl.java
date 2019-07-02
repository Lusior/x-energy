package com.xhkj.server.energy.dao.mybatis.impl;

import javax.annotation.Resource;

import com.xhkj.server.energy.dao.mybatis.vo.ValveCompanyExample;
import com.xhkj.server.energy.shiro.ShiroUtil;
import org.springframework.stereotype.Repository;

import com.xhkj.server.energy.dao.ValveCompanyDao;
import com.xhkj.server.energy.dao.mybatis.mapper.ValveCompanyMapper;
import com.xhkj.server.energy.dao.mybatis.vo.ValveCompany;

import java.util.List;

@Repository
public class ValveCompanyDaoImpl implements ValveCompanyDao {

    @Resource
    private ValveCompanyMapper valveCompanyMapper;

    @Override
    public void save(ValveCompany valveCompany) {
        valveCompanyMapper.insert(valveCompany);
    }

    @Override
    public ValveCompany get(Integer id) {
        return valveCompanyMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(ValveCompany valveCompany) {
        valveCompanyMapper.updateByPrimaryKey(valveCompany);
    }

    @Override
    public void delete(Integer id) {
        valveCompanyMapper.deleteByPrimaryKey(id);
    }

    ////*******自定义开始********//

    @Override
    public List<ValveCompany> getAll() {
        ValveCompanyExample example = new ValveCompanyExample();
        return valveCompanyMapper.selectByExample(example);
    }

    //**********自定义结束*****////

}
