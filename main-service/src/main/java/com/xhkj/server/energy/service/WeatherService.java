package com.xhkj.server.energy.service;

import com.xhkj.server.energy.dao.mybatis.vo.WeatherData;
import com.xhkj.server.energy.dao.mybatis.vo.WeatherDataStal;

import java.util.List;
import java.util.Map;

public interface WeatherService {
    WeatherDataStal getWeatherStalCurrent();

    WeatherData findWeatherCurr();

    List<WeatherData> getWeather7Day();

    List<WeatherData> getDay4Data();

    List<WeatherDataStal> getWeatherStalByDay(String day);

    List<WeatherData> getAvgTemList(Map<String, String> map);
}
