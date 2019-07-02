package com.xhkj.server.energy.dao.mybatis.impl;

import javax.annotation.Resource;

import com.xhkj.server.energy.dao.mybatis.vo.PredictStationExample;
import com.xhkj.server.energy.shiro.ShiroUtil;
import org.springframework.stereotype.Repository;

import com.xhkj.server.energy.dao.PredictStationDao;
import com.xhkj.server.energy.dao.mybatis.mapper.PredictStationMapper;
import com.xhkj.server.energy.dao.mybatis.vo.PredictStation;
import com.xhkj.server.energy.dao.mybatis.vo.PredictStationKey;

import java.util.Date;
import java.util.List;

@Repository
public class PredictStationDaoImpl implements PredictStationDao {

    @Resource
    private PredictStationMapper predictStationMapper;

    @Override
    public void save(PredictStation predictStation) {
        predictStationMapper.insert(predictStation);
    }

    @Override
    public PredictStation get(PredictStationKey predictStationKey) {
        return predictStationMapper.selectByPrimaryKey(predictStationKey);
    }

    @Override
    public void update(PredictStation predictStation) {
        predictStationMapper.updateByPrimaryKey(predictStation);
    }

    @Override
    public void delete(PredictStationKey predictStationKey) {
        predictStationMapper.deleteByPrimaryKey(predictStationKey);
    }

    ////*******自定义开始********//

    @Override
    public List<PredictStation> getByDate(Date date) {
        PredictStationExample example = new PredictStationExample();
        example.createCriteria().andCompanyIdEqualTo(ShiroUtil.getCompanyId()).andOpTimeEqualTo(date);
        return predictStationMapper.selectByExample(example);
    }

    //**********自定义结束*****////

}
