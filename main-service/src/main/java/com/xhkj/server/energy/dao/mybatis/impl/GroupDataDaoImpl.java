package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.GroupDataDao;
import com.xhkj.server.energy.dao.mybatis.mapper.GroupDataMapper;
import com.xhkj.server.energy.dao.mybatis.vo.GroupData;
import com.xhkj.server.energy.dao.mybatis.vo.GroupDataKey;
import com.xhkj.server.energy.dao.mybatis.vo.StandData;
import com.xhkj.server.energy.dao.mybatis.vo.StandDataKey;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class GroupDataDaoImpl implements GroupDataDao {

    @Resource
    private GroupDataMapper groupDataMapper;

    @Override
    public void save(GroupData groupData) {
        groupDataMapper.insert(groupData);
    }

    @Override
    public GroupData get(GroupDataKey groupDataKey) {
        return groupDataMapper.selectByPrimaryKey(groupDataKey);
    }

    @Override
    public void update(GroupData groupData) {
        groupDataMapper.updateByPrimaryKey(groupData);
    }

    @Override
    public void delete(GroupDataKey groupDataKey) {
        groupDataMapper.deleteByPrimaryKey(groupDataKey);
    }

    ////*******自定义开始********//

    @Override
    public void saveOrUpdate(GroupData groupData) {
        GroupDataKey key = new GroupDataKey();
        key.setCompanyId(groupData.getCompanyId());
        key.setGroupId(groupData.getGroupId());
        GroupData standData1 = get(key);
        if (standData1 == null) {
            save(groupData);
        } else {
            update(groupData);
        }
    }

    //**********自定义结束*****////

}
