package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.DataBasic;

import java.util.List;

public interface DataBasicDao extends Dao<DataBasic, Integer> {

    ////*******自定义开始********//

    List<DataBasic> getNoProcessedList(int count);

    DataBasic getLastLegal(int company, String standId);

    //**********自定义结束*****////

}
