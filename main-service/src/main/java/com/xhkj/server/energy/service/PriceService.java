package com.xhkj.server.energy.service;

import com.xhkj.server.energy.dao.mybatis.vo.Price;

import java.util.List;

public interface PriceService {

    Price getById(int id);

    List<Price> getAll();

    Price getPriceData();

    Price get(int id);

    boolean updatePrice(Price price);

    boolean insertPrice(Price price);
}
