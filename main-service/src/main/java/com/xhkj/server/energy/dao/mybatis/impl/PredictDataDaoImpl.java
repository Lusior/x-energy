package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.PredictDataDao;
import com.xhkj.server.energy.dao.mybatis.mapper.PredictDataMapper;
import com.xhkj.server.energy.dao.mybatis.vo.PredictData;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class PredictDataDaoImpl implements PredictDataDao {

    @Resource
    private PredictDataMapper predictDataMapper;

    @Override
    public void save(PredictData predictData) {
        predictDataMapper.insert(predictData);
    }

    @Override
    public PredictData get(String cityName) {
        return predictDataMapper.selectByPrimaryKey(cityName);
    }

    @Override
    public void update(PredictData predictData) {
        predictDataMapper.updateByPrimaryKey(predictData);
    }

    @Override
    public void delete(String cityName) {
        predictDataMapper.deleteByPrimaryKey(cityName);
    }

    ////*******自定义开始********//
    @Override
    public PredictData getThePredict() {
        return predictDataMapper.getThePredict();
    }

    @Override
    public Integer updateHaErBinSelective(PredictData thePredict) {
        return predictDataMapper.updateHaErBinSelective(thePredict);
    }

    //**********自定义结束*****////

}
