package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.PredictHistoryDataDao;
import com.xhkj.server.energy.dao.mybatis.mapper.PredictHistoryDataMapper;
import com.xhkj.server.energy.dao.mybatis.vo.PredictHistoryData;
import com.xhkj.server.energy.page.Page;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class PredictHistoryDataDaoImpl implements PredictHistoryDataDao {

    @Resource
    private PredictHistoryDataMapper predictHistoryDataMapper;

    @Override
    public void save(PredictHistoryData predictHistoryData) {
        predictHistoryDataMapper.insert(predictHistoryData);
    }

    @Override
    public PredictHistoryData get(Date opTime) {
        return predictHistoryDataMapper.selectByPrimaryKey(opTime);
    }

    @Override
    public void update(PredictHistoryData predictHistoryData) {
        predictHistoryDataMapper.updateByPrimaryKey(predictHistoryData);
    }

    @Override
    public void delete(Date opTime) {
        predictHistoryDataMapper.deleteByPrimaryKey(opTime);
    }

    ////*******自定义开始********//
    @Override
    public List<PredictHistoryData> getPredictHistoryList(Page<PredictHistoryData> page) {
        return predictHistoryDataMapper.getPredictHistoryList(page);
    }

    @Override
    public Integer updateByOpTimeSelective(PredictHistoryData predictHistoryData) {
        return predictHistoryDataMapper.updateByOpTimeSelective(predictHistoryData);
    }

    @Override
    public Integer insert(PredictHistoryData predictHistoryData) {
        return predictHistoryDataMapper.insert(predictHistoryData);
    }

    @Override
    public PredictHistoryData getByOpTime(Date yesterdayDate) {
        return predictHistoryDataMapper.getByOpTime(yesterdayDate);
    }
    //**********自定义结束*****////

}
