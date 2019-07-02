package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.StandBasicData;
import com.xhkj.server.energy.dao.mybatis.vo.StandBasicDataKey;

import java.util.List;

public interface StandBasicDataDao extends Dao<StandBasicData, StandBasicDataKey> {

    ////*******自定义开始********//

    List<StandBasicData> getByCompany(int company);

    //**********自定义结束*****////

}
