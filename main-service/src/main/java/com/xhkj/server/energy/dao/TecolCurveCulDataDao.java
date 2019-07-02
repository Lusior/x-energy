package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.TecolCurveCulData;
import com.xhkj.server.energy.dao.mybatis.vo.TecolCurveCulDataKey;

import java.util.List;

public interface TecolCurveCulDataDao extends Dao<TecolCurveCulData, TecolCurveCulDataKey> {

    ////*******自定义开始********//
    List<TecolCurveCulData> getAll();
    //**********自定义结束*****////

}
