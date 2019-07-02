package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.StationData;
import com.xhkj.server.energy.dao.mybatis.vo.StationDataKey;

import java.util.List;

public interface StationDataDao extends Dao<StationData, StationDataKey> {

    ////*******自定义开始********//

    List<StationData> getAll();
    List<StationData> getStationListByCompanyID(Integer companyID);

    //**********自定义结束*****////

}
