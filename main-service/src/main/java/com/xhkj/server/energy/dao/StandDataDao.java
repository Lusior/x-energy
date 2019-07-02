package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.StandData;
import com.xhkj.server.energy.dao.mybatis.vo.StandDataKey;

import java.util.List;

public interface StandDataDao extends Dao<StandData, StandDataKey> {

    ////*******自定义开始********//

    List<StandData> getAll();//这里面默认有componyId了。 但是在PostConstruct没有。所以加一下下面的
    //方便本地调试用
    List<StandData> getStandListByCompanyId(Integer componyId);
    //**********自定义结束*****////

}
