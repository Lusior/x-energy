package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.Company;

import java.util.List;

public interface CompanyDao extends Dao<Company, Integer> {

    ////*******自定义开始********//

    List<Company> findAll();

    //**********自定义结束*****////

}
