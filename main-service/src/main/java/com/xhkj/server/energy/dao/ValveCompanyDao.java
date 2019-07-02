package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.ValveCompany;
import java.lang.Integer;
import java.util.List;

public interface ValveCompanyDao extends Dao<ValveCompany, Integer> {

    ////*******自定义开始********//
    List<ValveCompany> getAll();
    //**********自定义结束*****////

}
