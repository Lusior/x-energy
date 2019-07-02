package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.WeatherData;

import java.util.List;
import java.util.Map;

public interface WeatherDataDao extends Dao<WeatherData, Integer> {

    ////*******自定义开始********//
    List<WeatherData> getAvgTemList(Map<String, String> map);

    List<WeatherData> getInDays(String[] days);
    //**********自定义结束*****////

}
