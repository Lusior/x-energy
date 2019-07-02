package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.WaterTemperatureDataDao;
import com.xhkj.server.energy.dao.mybatis.mapper.WaterTemperatureDataMapper;
import com.xhkj.server.energy.dao.mybatis.vo.WaterTemperatureData;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class WaterTemperatureDataDaoImpl implements WaterTemperatureDataDao {

    @Resource
    private WaterTemperatureDataMapper waterTemperatureDataMapper;

    @Override
    public void save(WaterTemperatureData waterTemperatureData) {
        waterTemperatureDataMapper.insert(waterTemperatureData);
    }

    @Override
    public WaterTemperatureData get(Short temperature) {
        return waterTemperatureDataMapper.selectByPrimaryKey(temperature);
    }

    @Override
    public void update(WaterTemperatureData waterTemperatureData) {
        waterTemperatureDataMapper.updateByPrimaryKey(waterTemperatureData);
    }

    @Override
    public void delete(Short temperature) {
        waterTemperatureDataMapper.deleteByPrimaryKey(temperature);
    }

    ////*******自定义开始********//
    //**********自定义结束*****////

}
