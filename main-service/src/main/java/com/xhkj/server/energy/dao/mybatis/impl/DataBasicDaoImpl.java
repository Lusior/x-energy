package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.DataBasicDao;
import com.xhkj.server.energy.dao.mybatis.mapper.DataBasicMapper;
import com.xhkj.server.energy.dao.mybatis.vo.DataBasic;
import com.xhkj.server.energy.dao.mybatis.vo.DataBasicExample;
import com.xhkj.server.energy.utils.DateUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class DataBasicDaoImpl implements DataBasicDao {

    @Resource
    private DataBasicMapper dataBasicMapper;

    @Override
    public void save(DataBasic dataBasic) {
        dataBasicMapper.insert(dataBasic);
    }

    @Override
    public DataBasic get(Integer id) {
        return dataBasicMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(DataBasic dataBasic) {
        dataBasicMapper.updateByPrimaryKey(dataBasic);
    }

    @Override
    public void delete(Integer id) {
        dataBasicMapper.deleteByPrimaryKey(id);
    }

    ////*******自定义开始********//

    @Override
    public List<DataBasic> getNoProcessedList(int count) {
        DataBasicExample example = new DataBasicExample();
        Date date = DateUtils.addDays(new Date(), -100);
        example.createCriteria().andOpTimeGreaterThanOrEqualTo(date)
                .andProcessedIsNull();
        example.setOrderByClause("OP_TIME asc limit " + count);
        return dataBasicMapper.selectByExample(example);
    }

    @Override
    public DataBasic getLastLegal(int company, String standId) {
        DataBasicExample example = new DataBasicExample();
        example.createCriteria().andCompanyIdEqualTo(company).andStandIdEqualTo(standId).andPt1Between(0.01f, 0.99f);
        example.setOrderByClause("OP_TIME desc limit 1");
        List<DataBasic> dataBasics = dataBasicMapper.selectByExample(example);
        if (dataBasics != null && dataBasics.size() > 0) {
            return dataBasics.get(0);
        }
        return null;
    }

    //**********自定义结束*****////

}
