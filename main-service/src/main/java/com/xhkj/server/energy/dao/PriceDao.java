package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.Price;

import java.util.List;

public interface PriceDao extends Dao<Price, Integer> {

    ////*******自定义开始********//
    List<Price> getAll();

    Price getPriceData();
    //**********自定义结束*****////

}
