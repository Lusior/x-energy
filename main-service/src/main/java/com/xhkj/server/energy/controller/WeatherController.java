package com.xhkj.server.energy.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xhkj.server.energy.dao.mybatis.vo.WeatherData;
import com.xhkj.server.energy.dao.mybatis.vo.WeatherDataStal;
import com.xhkj.server.energy.page.BootstrapTablePage;
import com.xhkj.server.energy.page.BootstrapTableParams;
import com.xhkj.server.energy.service.WeatherService;
import com.xhkj.server.energy.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/weather")
public class WeatherController extends BaseController<WeatherData> {

    @Autowired
    private WeatherService weatherService;

    @RequiresPermissions("weather_list:view")
    @RequestMapping("/pandect")
    public ModelAndView pandect() {
        ModelAndView model = new ModelAndView("weather/weather");
        WeatherDataStal weatherDataStal = weatherService.getWeatherStalCurrent();
        if (weatherDataStal == null) {
            return model;
        }
        model.addObject("weatherDataStal", weatherDataStal);
        String tm = weatherDataStal.getCrtTm();
        model.addObject("shikuang", tm.substring(0, 2) + ":" + tm.substring(2, 4));

        String winLevl = "0";
        String winName = "无风";
        if (null != weatherDataStal.getWinSMax()) {
            float bd = weatherDataStal.getWinSMax().floatValue();
            if (bd >= 0f && bd <= 0.2f) {
                winLevl = "0";
                winName = "无风";
            } else if (bd >= 0.3f && bd <= 1.5f) {
                winLevl = "1";
                winName = "软风";
            } else if (bd >= 1.6f && bd <= 3.3f) {
                winLevl = "2";
                winName = "轻风";
            } else if (bd >= 3.4f && bd <= 5.4f) {
                winLevl = "3";
                winName = "微风";
            } else if (bd >= 5.5f && bd <= 7.9f) {
                winLevl = "4";
                winName = "和风";
            } else if (bd >= 8.0f && bd <= 10.7f) {
                winLevl = "5";
                winName = "清风";
            } else if (bd >= 10.8f && bd <= 13.8f) {
                winLevl = "6";
                winName = "强风";
            } else if (bd >= 13.9f && bd <= 17.1f) {
                winLevl = "7";
                winName = "疾风";
            } else if (bd >= 17.2f && bd <= 20.7f) {
                winLevl = "8";
                winName = "大风";
            } else if (bd >= 20.8f && bd <= 24.4f) {
                winLevl = "9";
                winName = "烈风";
            } else if (bd >= 24.5f) {
                winLevl = "10";
                winName = "狂风";
            }
        }
        BigDecimal temInt = new BigDecimal(weatherDataStal.getTem().intValue());
        model.addObject("winLevl", winLevl);
        model.addObject("winName", winName);
        BigDecimal zero = BigDecimal.ZERO;
        BigDecimal pb = new BigDecimal(40);
        BigDecimal dd = new BigDecimal(1.4);
        //以0度 像素40为标准,每增加一度就会40+0.8 [40 + (温度-zero)*0.8]
        BigDecimal xiangsu = pb.add(temInt.subtract(zero).multiply(dd));
        model.addObject("xiangsu", xiangsu.toString());
        //取整的温度
        model.addObject("wendu", temInt);

        List<WeatherData> weatherDataList = weatherService.getWeather7Day();
        if (weatherDataList != null) {
            model.addObject("data7", weatherDataList);
            //为当天天气
            for (WeatherData weatherData : weatherDataList) {
                if (DateUtils.getYmdDate().equals(weatherData.getDay())) {
                    model.addObject("weatherData", weatherData);
                    //日期
                    model.addObject("dt", weatherData.getCrtDt().substring(6));
                }
            }
        }
        return model;
    }

    @RequestMapping("/weatherDetail")
    public List<WeatherData> weatherDetail() {
        return weatherService.getDay4Data();
    }

    @RequiresPermissions("weather_detail:view")
    @RequestMapping("/details")
    public ModelAndView details() {
//        pageInitToSession(session);
        ModelAndView model = new ModelAndView("weather/weather_detail");
        //获取4天详细信息
        List<WeatherData> list = weatherService.getDay4Data();
        //4天天气信息
        WeatherData wd0 = null, wd1 = null, wd2 = null, wd3 = null;
        //4天 24小時温度json
        String hour0, hour1, hour2, hour3;
        //4天  24小時时间信息
        StringBuilder h0_d = new StringBuilder();
        StringBuilder h1_d = new StringBuilder();
        StringBuilder h2_d = new StringBuilder();
        StringBuilder h3_d = new StringBuilder();
        //4天  24小時温度信息
        StringBuilder h0_w = new StringBuilder();
        StringBuilder h1_w = new StringBuilder();
        StringBuilder h2_w = new StringBuilder();
        StringBuilder h3_w = new StringBuilder();

        if (list.size() > 0) {
            wd0 = list.get(0);
            hour0 = wd0.getHour3Data();
            h0_d = new StringBuilder();
            h0_w = new StringBuilder();
            List<Map<String, Object>> listHour0 = new Gson().fromJson(hour0, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
            for (int i = 0; i < listHour0.size(); i++) {
                h0_d.append("'").append(listHour0.get(i).get("d")).append("'");
                h0_w.append(listHour0.get(i).get("w"));
                if (i < listHour0.size() - 1) {
                    h0_d.append(",");
                    h0_w.append(",");
                }
            }
        }
        if (list.size() > 1) {
            wd1 = list.get(1);
            hour1 = wd1.getHour3Data();
            h1_d = new StringBuilder();
            h1_w = new StringBuilder();
            List<Map<String, Object>> listHour1 = new Gson().fromJson(hour1, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
            for (int i = 0; i < listHour1.size(); i++) {
                h1_d.append("'").append(listHour1.get(i).get("d")).append("'");
                h1_w.append(listHour1.get(i).get("w"));
                if (i < listHour1.size() - 1) {
                    h1_d.append(",");
                    h1_w.append(",");
                }
            }
        }
        if (list.size() > 2) {
            wd2 = list.get(2);
            hour2 = wd2.getHour3Data();
            h2_d = new StringBuilder();
            h2_w = new StringBuilder();
            List<Map<String, Object>> listHour2 = new Gson().fromJson(hour2, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
            for (int i = 0; i < listHour2.size(); i++) {
                h2_d.append("'").append(listHour2.get(i).get("d")).append("'");
                h2_w.append(listHour2.get(i).get("w"));
                if (i < listHour2.size() - 1) {
                    h2_d.append(",");
                    h2_w.append(",");
                }
            }
        }
        if (list.size() > 3) {
            wd3 = list.get(3);
            hour3 = wd3.getHour3Data();
            h3_d = new StringBuilder();
            h3_w = new StringBuilder();
            List<Map<String, Object>> listHour3 = new Gson().fromJson(hour3, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
            for (int i = 0; i < listHour3.size(); i++) {
                h3_d.append("'").append(listHour3.get(i).get("d")).append("'");
                h3_w.append(listHour3.get(i).get("w"));
                if (i < listHour3.size() - 1) {
                    h3_d.append(",");
                    h3_w.append(",");
                }
            }
        }
        model.addObject("wd0", wd0);
        model.addObject("wd1", wd1);
        model.addObject("wd2", wd2);
        model.addObject("wd3", wd3);
        model.addObject("h0_d", h0_d.toString());
        model.addObject("h0_w", h0_w.toString());
        model.addObject("h1_d", h1_d.toString());
        model.addObject("h1_w", h1_w.toString());
        model.addObject("h2_d", h2_d.toString());
        model.addObject("h2_w", h2_w.toString());
        model.addObject("h3_d", h3_d.toString());
        model.addObject("h3_w", h3_w.toString());
        return model;
    }

    @RequiresPermissions("weather_situation:view")
    @RequestMapping("/situation")
    public ModelAndView situation() {
//        pageInitToSession(session);
        return new ModelAndView("weather/weather_situation");
    }

    @RequiresPermissions("weather_situation:view")
    @RequestMapping("/situation/list")
    @ResponseBody
    public BootstrapTablePage<WeatherData> list(BootstrapTableParams params) {
        initParams(params);
        String beginTime = params.getParams().get("beginTime");
        String endTime = params.getParams().get("endTime");
        if (StringUtils.isNotEmpty(beginTime)) {
            params.getParams().put("beginTime", beginTime.replaceAll("-", ""));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            params.getParams().put("endTime", endTime.replaceAll("-", ""));
        }
        PageHelper.startPage(params.getPageNum(), params.getLimit());
        List<WeatherData> avgTemList = weatherService.getAvgTemList(params.getParams());
        return new BootstrapTablePage<>(avgTemList);
    }

    @RequiresPermissions("weather_situation:view")
    @RequestMapping("/situation/day/hour/weather")
    @ResponseBody
    public List<WeatherDataStal> dayHourWeather(String day) {
        if (StringUtils.isNotEmpty(day)) {
            return weatherService.getWeatherStalByDay(day);
        }
        return null;
    }

}
