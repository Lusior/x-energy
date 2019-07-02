package com.xhkj.server.energy.dao.mybatis.impl;

import javax.annotation.Resource;

import com.xhkj.server.energy.dao.mybatis.vo.TecolCurveDataExample;
import org.springframework.stereotype.Repository;

import com.xhkj.server.energy.dao.TecolCurveDataDao;
import com.xhkj.server.energy.dao.mybatis.mapper.TecolCurveDataMapper;
import com.xhkj.server.energy.dao.mybatis.vo.TecolCurveData;
import java.lang.Short;

@Repository
public class TecolCurveDataDaoImpl implements TecolCurveDataDao {

    @Resource
    private TecolCurveDataMapper tecolCurveDataMapper;

    @Override
    public void save(TecolCurveData tecolCurveData) {
        tecolCurveDataMapper.insert(tecolCurveData);
    }

    @Override
    public TecolCurveData get(Short temperature) {
        return tecolCurveDataMapper.selectByPrimaryKey(temperature);
    }

    @Override
    public void update(TecolCurveData tecolCurveData) {
        tecolCurveDataMapper.updateByPrimaryKey(tecolCurveData);
    }

    @Override
    public void delete(Short temperature) {
        tecolCurveDataMapper.deleteByPrimaryKey(temperature);
    }

    ////*******自定义开始********//

    @Override
    public TecolCurveData getByTemperature(Short temperature) {
        TecolCurveDataExample example = new TecolCurveDataExample();
        example.createCriteria().andTemperatureEqualTo(temperature);
        return tecolCurveDataMapper.selectByExample(example).get(0);
    }

    //**********自定义结束*****////

}
