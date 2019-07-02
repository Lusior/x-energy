package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.CompanyDao;
import com.xhkj.server.energy.dao.mybatis.mapper.CompanyMapper;
import com.xhkj.server.energy.dao.mybatis.vo.Company;
import com.xhkj.server.energy.dao.mybatis.vo.CompanyExample;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class CompanyDaoImpl implements CompanyDao {

    @Resource
    private CompanyMapper companyMapper;

    @Override
    public void save(Company company) {
        companyMapper.insert(company);
    }

    @Override
    public Company get(Integer id) {
        return companyMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Company company) {
        companyMapper.updateByPrimaryKey(company);
    }

    @Override
    public void delete(Integer id) {
        companyMapper.deleteByPrimaryKey(id);
    }

    ////*******自定义开始********//

    @Override
    public List<Company> findAll() {
        return companyMapper.selectByExample(new CompanyExample());
    }

    //**********自定义结束*****////

}
