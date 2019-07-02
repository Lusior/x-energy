package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.WeatherDataStalDao;
import com.xhkj.server.energy.dao.mybatis.mapper.WeatherDataStalMapper;
import com.xhkj.server.energy.dao.mybatis.vo.WeatherDataStal;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class WeatherDataStalDaoImpl implements WeatherDataStalDao {

    @Resource
    private WeatherDataStalMapper weatherDataStalMapper;

    @Override
    public void save(WeatherDataStal weatherDataStal) {
        weatherDataStalMapper.insert(weatherDataStal);
    }

    @Override
    public WeatherDataStal get(Integer id) {
        return weatherDataStalMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(WeatherDataStal weatherDataStal) {
        weatherDataStalMapper.updateByPrimaryKey(weatherDataStal);
    }

    @Override
    public void delete(Integer id) {
        weatherDataStalMapper.deleteByPrimaryKey(id);
    }

    ////*******自定义开始********//

    @Override
    public List<WeatherDataStal> getByDay(String day) {
        return weatherDataStalMapper.getListByDay(day);
    }

    /**
     * 获取一条今日的最新记录
     */
    @Override
    public WeatherDataStal getWeatherStalCurrent(String day) {
        return weatherDataStalMapper.getWeatherStalCurrent(day);
    }

    //**********自定义结束*****////

}
