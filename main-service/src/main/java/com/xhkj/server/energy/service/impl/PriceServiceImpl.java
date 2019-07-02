package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.PriceDao;
import com.xhkj.server.energy.dao.mybatis.vo.Price;
import com.xhkj.server.energy.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceDao priceDao;

    @Override
    public Price getById(int id) {
        return priceDao.get(id);
    }

    @Override
    public List<Price> getAll() {
        return priceDao.getAll();
    }

    @Override
    public Price getPriceData() {
        return priceDao.getPriceData();
    }

    @Override
    public Price get(int id) {
        return priceDao.get(id);
    }

    @Override
    public boolean updatePrice(Price price) {
        priceDao.update(price);
        return true;
    }

    @Override
    public boolean insertPrice(Price price) {
        priceDao.save(price);
        return true;
    }
}
