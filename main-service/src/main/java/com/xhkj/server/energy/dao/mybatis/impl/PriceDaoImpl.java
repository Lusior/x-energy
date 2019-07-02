package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.PriceDao;
import com.xhkj.server.energy.dao.mybatis.mapper.PriceMapper;
import com.xhkj.server.energy.dao.mybatis.vo.Price;
import com.xhkj.server.energy.dao.mybatis.vo.PriceExample;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class PriceDaoImpl implements PriceDao {

    @Resource
    private PriceMapper priceMapper;

    @Override
    public void save(Price price) {
        priceMapper.insert(price);
    }

    @Override
    public Price get(Integer companyId) {
        return priceMapper.selectByPrimaryKey(companyId);
    }

    @Override
    public void update(Price price) {
        priceMapper.updateByPrimaryKey(price);
    }

    @Override
    public void delete(Integer companyId) {
        priceMapper.deleteByPrimaryKey(companyId);
    }

    ////*******自定义开始********//

    @Override
    public List<Price> getAll() {
        return priceMapper.selectByExample(new PriceExample());
    }

    @Override
    public Price getPriceData() {
        PriceExample example = new PriceExample();
        example.setOrderByClause("COMPANY_ID desc limit 1");
        return priceMapper.selectByExample(example).get(0);
    }

    //**********自定义结束*****////

}
