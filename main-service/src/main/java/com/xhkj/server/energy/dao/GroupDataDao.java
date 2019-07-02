package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.GroupData;
import com.xhkj.server.energy.dao.mybatis.vo.GroupDataKey;

public interface GroupDataDao extends Dao<GroupData, GroupDataKey> {

    ////*******自定义开始********//

    void saveOrUpdate(GroupData groupData);

    //**********自定义结束*****////

}
