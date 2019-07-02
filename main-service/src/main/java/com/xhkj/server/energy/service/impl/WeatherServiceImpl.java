package com.xhkj.server.energy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xhkj.server.energy.dao.WeatherDataDao;
import com.xhkj.server.energy.dao.WeatherDataStalDao;
import com.xhkj.server.energy.dao.mybatis.vo.WeatherData;
import com.xhkj.server.energy.dao.mybatis.vo.WeatherDataStal;
import com.xhkj.server.energy.service.WeatherService;
import com.xhkj.server.energy.utils.DateUtils;
import com.xhkj.server.energy.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    WeatherDataDao weatherDataDao;
    @Autowired
    WeatherDataStalDao weatherDataStalDao;

    private final static String CITY_NO = "101050201";

    private final static String STA_ID = "haerbin";
    //中国气象网站 哈尔滨地址7天
    private final static String WEATHER_URL_7DAY = "http://www.weather.com.cn/weather/101050101.shtml";
    //private final static String WEATHER_URL_7DAY = "http://www.weather.com.cn/weathern/101050101.shtml";

    @Override
    public List<WeatherData> getAvgTemList(Map<String, String> map) {
        return weatherDataDao.getAvgTemList(map);
    }

    @Override
    public List<WeatherDataStal> getWeatherStalByDay(String day) {
        return weatherDataStalDao.getByDay(day);
    }

    @Override
    public WeatherDataStal getWeatherStalCurrent() {
        return weatherDataStalDao.getWeatherStalCurrent(DateUtils.getYmdDate());
    }

    @Override
    public WeatherData findWeatherCurr() {
        return weatherDataDao.getInDays(new String[]{DateUtils.getYmdDate()}).get(0);
    }

    @Override
    public List<WeatherData> getDay4Data() {
        String[] days = readyQueryParams(0, 4);
        return weatherDataDao.getInDays(days);
    }

    @Override
    public List<WeatherData> getWeather7Day() {
        String[] days = readyQueryParams(-3, 7);
        return weatherDataDao.getInDays(days);
    }

    /**
     * @param beginDay 从哪天开始：今天0，昨天-1，明天1，以此类推
     * @param total    获取几天
     * @return 准备好的数据，入参的每一天的具体日期
     */
    private String[] readyQueryParams(int beginDay, int total) {
        String[] days = new String[total];
        for (int i = 0; i < total; i++) {
            days[i] = DateUtils.dayAdd(beginDay + i, "yyyyMMdd");
        }
        return days;
    }

    @Scheduled(cron = "0 15 00/3 * * ? ")//每天00点15分触发,然后每3个小时执行
    public void webSearchWeather() {

        String html = HttpClientUtil.httpGetRequest(WEATHER_URL_7DAY, null);
        //获取3小时温度变化
        String data3 = "";
        Pattern p3 = Pattern.compile("hour3data=([\\s\\S]*?)</script>");// 匹配<title>开头，</title>结尾的文档
        Matcher m3 = p3.matcher(html);// 开始编译
        while (m3.find()) {
            data3 = m3.group(1);
        }
        Map<String, Object> bmap = new Gson().fromJson(data3, new TypeToken<Map<String, Object>>() {
        }.getType());
        ArrayList<Object> d7 = (ArrayList<Object>) bmap.get("7d");
        Document doc = Jsoup.parse(html);
        Elements es = doc.select("[class=t clearfix]");
        Element e = es.get(0);
        Elements situation = e.select("li");
        for (int i = 0; i < 4; i++){
            try {
                collectWeatherData(situation, d7, i);
            } catch (Exception ex) {
                //log.error("取后" + i + "日温度信息错误");
                ex.printStackTrace();
            }
        }
    }

    private void collectWeatherData(Elements situation, ArrayList<Object> d7, int whichDay) {
        Elements imgs = situation.get(whichDay).select("big");
        String img1 = imgs.get(0).attr("class").replace("png40 ", "");
        String img2 = imgs.get(1).attr("class").replace("png40 ", "");
        Elements wea = situation.get(whichDay).select(".wea");
        String weaStr = wea.get(0).text();
        String wea1 = weaStr;
        String wea2 = weaStr;
        if (weaStr.indexOf("转") > 0) {
            wea1 = weaStr.substring(0, weaStr.indexOf("转"));
            wea2 = weaStr.substring(weaStr.indexOf("转") + 1);
        }
        Elements wen = situation.get(whichDay).select(".tem");
        Float wen1 = Float.parseFloat(wen.select("span").text().replace("℃", ""));
        Float wen2 = Float.parseFloat(wen.select("i").text().replace("℃", ""));
        Float wenAvg = (wen1 + wen2) / 2;
        Elements feng = situation.get(whichDay).select(".win");
        String feng1 = feng.select("span").get(0).attr("title");
        String feng2 = feng1;
        if (feng.select("span").size() > 1) {
            feng2 = feng.select("span").get(1).attr("title");
        }
        String jfeng = feng.select("i").text();
        String jfeng1 = jfeng;
        String jfeng2 = jfeng;
        if (jfeng.indexOf("转") > 0) {
            jfeng1 = jfeng.substring(0, jfeng.indexOf("转"));
            jfeng2 = jfeng.substring(jfeng.indexOf("转") + 1);
        }
        List<Object> l = (List<Object>) d7.get(whichDay);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object o : l) {
            String[] ss = String.valueOf(o).split(",");
            Map<String, Object> map = new HashMap<>();
            map.put("d", ss[0]);
            map.put("i", ss[1]);
            map.put("y", ss[2]);
            map.put("w", ss[3].replace("℃", ""));
            map.put("f", ss[4]);
            map.put("j", ss[5]);
            list.add(map);
        }
        if (whichDay == 3){
            fixDay3Data(l, list);
        }
        String json = new Gson().toJson(list);
        WeatherData weatherData = new WeatherData();
        String day = DateUtils.dayAdd(whichDay, "yyyyMMdd");
        weatherData.setDay(day);
        weatherData.setTeC1(wea1);
        weatherData.setTeC2(wea2);
        weatherData.setTeH(wen1);
        weatherData.setTeL(wen2);
        weatherData.setTeA(wenAvg);
        weatherData.setTeWe1(jfeng1);
        weatherData.setTeWe2(jfeng2);
        weatherData.setTeWd1(feng1);
        weatherData.setTeWd2(feng2);
        weatherData.setTeI1(img1);
        weatherData.setTeI2(img2);
        weatherData.setHour3Data(json);
        weatherData.setCityNo(CITY_NO);
        weatherData.setCrtDt(DateUtils.getYmdDate());
        weatherData.setCrtTm(DateUtils.getHmsTime());
        //log.debug("采集后" + whichDay + "天信息:" + weatherData.toString());
        if (!weatherDataDao.getInDays(new String[]{day}).isEmpty()) { //查看是否存在天气预报
            weatherDataDao.update(weatherData);
        } else {
            weatherDataDao.save(weatherData);
        }
    }

    private void fixDay3Data(List<Object> l, List<Map<String, Object>> list) {
        //处理后三天的时间不足问题
        if (l.size() < 8) {
            Map<String, Object> m0 = list.get(0);
            Map<String, Object> m1 = list.get(1);
            Map<String, Object> m2 = list.get(2);
            Map<String, Object> m3 = list.get(3);

            Map<String, Object> m0_1 = new HashMap<>(m0);
            m0_1.put("d", ((String) m0_1.get("d")).replace("08", "11"));
            m0_1.put("w", (Integer.parseInt((String) m0.get("w")) + Integer.parseInt((String) m1.get("w"))) / 2);

            Map<String, Object> m1_2 = new HashMap<>(m1);
            m1_2.put("d", ((String) m1_2.get("d")).replace("14", "17"));
            m1_2.put("w", (Integer.parseInt((String) m1.get("w")) + Integer.parseInt((String) m2.get("w"))) / 2);

            Map<String, Object> m2_3 = new HashMap<>(m2);
            m2_3.put("d", ((String) m2_3.get("d")).replace("20", "23"));
            m2_3.put("w", (Integer.parseInt((String) m2.get("w")) + Integer.parseInt((String) m3.get("w"))) / 2);

            Map<String, Object> m3_4 = new HashMap<>(m3);
            m3_4.put("d", ((String) m3_4.get("d")).replace("20", "23"));
            m3_4.put("w", (Integer.parseInt((String) m3.get("w"))) - 1);

            list.add(1, m0_1);
            list.add(3, m1_2);
            list.add(5, m2_3);
            list.add(7, m3_4);
        }
    }

    @Scheduled(cron = "0 0 0/1 * * ? ")//每一小时执行一次
    public void apiData() {
        String url = "https://free-api.heweather.com/s6/weather?location=" + STA_ID + "&key=f20bdb09fa004a70a4748f93a8ec1097";//哈尔滨
        String html = HttpClientUtil.httpGetRequest(url, null);

        JSONObject object = (JSONObject) JSON.parse(html);
        String heWeather6 = object.getString("HeWeather6");
        System.out.println(heWeather6);
        JSONArray arrayJsonObject = (JSONArray) JSON.parse(heWeather6);
        String jsonStr = arrayJsonObject.getJSONObject(0).toString();
        JSONObject weatherObject = (JSONObject) JSON.parse(jsonStr);

        String status = weatherObject.getString("status");
        if ("ok".equals(status)) {

            String basicStr = weatherObject.getString("basic");//基础信息
            JSONObject basicObject = (JSONObject) JSON.parse(basicStr);

            String nowStr = weatherObject.getString("now");//实况天气
            JSONObject nowObject = (JSONObject) JSON.parse(nowStr);
            System.out.println(nowObject.toString());

            String dailyArrayStr = weatherObject.getString("daily_forecast");//天气预报
            JSONArray dailyArrayJsonObject = (JSONArray) JSON.parse(dailyArrayStr);
            String dailyArrayJsonStr = dailyArrayJsonObject.getJSONObject(0).toString();
            JSONObject dailyJsonObject = (JSONObject) JSON.parse(dailyArrayJsonStr);
            System.out.println(dailyJsonObject.toString());

            String prs = nowObject.getString("pres");//气压
            String prsSea = nowObject.getString("pres");//海平面气压
            String prsMax = nowObject.getString("pres");//最高气压
            String prsMin = nowObject.getString("pres");//最低气压
            String tem = nowObject.getString("tmp");//温度/气温
            String temMax = dailyJsonObject.getString("tmp_max");//最高气温
            String temMin = dailyJsonObject.getString("tmp_min");//最低气温
            String rhu = dailyJsonObject.getString("hum");//相对湿度
            String rhuMin = dailyJsonObject.getString("hum");//相对最小湿度
            String vap = nowObject.getString("pres");//水气压
            String pre1h = dailyJsonObject.getString("pcpn");//降水量
            String winDInstMax = nowObject.getString("wind_deg");//极大风速的风向(角度) 字符
            String winSMax = nowObject.getString("wind_spd");//最大风速 风速，公里/小时
            String winSInstMax = nowObject.getString("wind_spd");//极大风速 风速，公里/小时
            String winDSMax = nowObject.getString("wind_deg");//最大风速的风向(角度)  度
            String winSAvg10Mi = nowObject.getString("wind_spd");//10分钟平均风速风速，公里/小时
            String winDAvg10mi = nowObject.getString("wind_deg");//10分钟平均风向(角度)	度

            WeatherDataStal weatherDataStal = new WeatherDataStal();
            weatherDataStal.setStaId(STA_ID);
            //日期处理
            Calendar c = Calendar.getInstance();
            String year = String.valueOf(c.get(Calendar.YEAR));//获取年份
            String month = String.valueOf(c.get(Calendar.MONTH) + 1);//获取月份
            String day = String.valueOf(c.get(Calendar.DATE));//获取日
            String minute = String.valueOf(c.get(Calendar.MINUTE));//分
            int hour = c.get(Calendar.HOUR);//小时
            String second = String.valueOf(c.get(Calendar.SECOND));//秒
            String WeekOfYear = String.valueOf(c.get(Calendar.DAY_OF_WEEK));//显示当前日期是一周的第几天，周一就是1，周五就是5

            if (month.length() < 2) {
                month = "0" + month;
            }
            if (day.length() < 2) {
                day = "0" + day;
            }

            String date = year + month + day;//格式：20160219
            weatherDataStal.setDay(date);
            weatherDataStal.setHour(hour);
            weatherDataStal.setPrs(new BigDecimal(prs));//气压
            weatherDataStal.setPrsSea(new BigDecimal(prsSea));//海平面气压
            weatherDataStal.setPrsMax(new BigDecimal(prsMax));//最高气压
            weatherDataStal.setPrsMin(new BigDecimal(prsMin));//最低气压
            weatherDataStal.setTem(new BigDecimal(tem));//温度/气温
            weatherDataStal.setTemMax(new BigDecimal(temMax));//最高气温
            weatherDataStal.setTemMin(new BigDecimal(temMin));//最低气温
            weatherDataStal.setRhu(new BigDecimal(rhu));//相对湿度
            weatherDataStal.setRhuMin(new BigDecimal(rhuMin));//相对最小湿度
            weatherDataStal.setVap(new BigDecimal(vap));//水气压
            weatherDataStal.setPre1h(new BigDecimal(pre1h));//降水量
            weatherDataStal.setWinDInstMax(new BigDecimal(winDInstMax));//极大风速的风向(角度) 字符
            weatherDataStal.setWinSMax(new BigDecimal(winSMax));//最大风速 米/秒
            weatherDataStal.setWinDSMax(new BigDecimal(winDSMax));//最大风速的风向(角度)  度
            weatherDataStal.setWinSInstMax(new BigDecimal(winSInstMax));//极大风速 米/秒
            weatherDataStal.setWinSAvg10mi(new BigDecimal(winSAvg10Mi));//10分钟平均风速米/秒
            weatherDataStal.setWinDAvg10mi(new BigDecimal(winDAvg10mi));//10分钟平均风向(角度)	度
            weatherDataStal.setCrtDt(DateUtils.getYmdDate());
            weatherDataStal.setCrtTm(DateUtils.getHmsTime());
            weatherDataStalDao.save(weatherDataStal);
        }
    }
}
