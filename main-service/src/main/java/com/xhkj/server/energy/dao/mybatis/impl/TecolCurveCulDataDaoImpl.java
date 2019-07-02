package com.xhkj.server.energy.dao.mybatis.impl;

import javax.annotation.Resource;

import com.xhkj.server.energy.dao.mybatis.vo.TecolCurveCulDataExample;
import org.springframework.stereotype.Repository;

import com.xhkj.server.energy.dao.TecolCurveCulDataDao;
import com.xhkj.server.energy.dao.mybatis.mapper.TecolCurveCulDataMapper;
import com.xhkj.server.energy.dao.mybatis.vo.TecolCurveCulData;
import com.xhkj.server.energy.dao.mybatis.vo.TecolCurveCulDataKey;

import java.util.List;

@Repository
public class TecolCurveCulDataDaoImpl implements TecolCurveCulDataDao {

    @Resource
    private TecolCurveCulDataMapper tecolCurveCulDataMapper;

    @Override
    public void save(TecolCurveCulData tecolCurveCulData) {
        tecolCurveCulDataMapper.insert(tecolCurveCulData);
    }

    @Override
    public TecolCurveCulData get(TecolCurveCulDataKey tecolCurveCulDataKey) {
        return tecolCurveCulDataMapper.selectByPrimaryKey(tecolCurveCulDataKey);
    }

    @Override
    public void update(TecolCurveCulData tecolCurveCulData) {
        tecolCurveCulDataMapper.updateByPrimaryKey(tecolCurveCulData);
    }

    @Override
    public void delete(TecolCurveCulDataKey tecolCurveCulDataKey) {
        tecolCurveCulDataMapper.deleteByPrimaryKey(tecolCurveCulDataKey);
    }

    ////*******自定义开始********//

    @Override
    public List<TecolCurveCulData> getAll() {
        return tecolCurveCulDataMapper.selectByExample(new TecolCurveCulDataExample());
    }

    //**********自定义结束*****////

}
