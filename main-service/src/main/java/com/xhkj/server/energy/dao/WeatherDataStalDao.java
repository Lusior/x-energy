package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.WeatherDataStal;

import java.util.List;

public interface WeatherDataStalDao extends Dao<WeatherDataStal, Integer> {

    ////*******自定义开始********//

    List<WeatherDataStal> getByDay(String day);
    WeatherDataStal getWeatherStalCurrent(String day);

    //**********自定义结束*****////

}
