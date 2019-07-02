package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.WeatherDataDao;
import com.xhkj.server.energy.dao.mybatis.mapper.WeatherDataMapper;
import com.xhkj.server.energy.dao.mybatis.vo.WeatherData;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class WeatherDataDaoImpl implements WeatherDataDao {

    @Resource
    private WeatherDataMapper weatherDataMapper;

    @Override
    public void save(WeatherData weatherData) {
        weatherDataMapper.insert(weatherData);
    }

    @Override
    public WeatherData get(Integer id) {
        return weatherDataMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(WeatherData weatherData) {
        weatherDataMapper.updateByPrimaryKey(weatherData);
    }

    @Override
    public void delete(Integer id) {
        weatherDataMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<WeatherData> getInDays(String[] days) {
        return weatherDataMapper.getInDays(days);
    }

    ////*******自定义开始********//

    @Override
    public List<WeatherData> getAvgTemList(Map<String, String> map) {
        return weatherDataMapper.getAvgTemList(map);
    }
    //**********自定义结束*****////

}
