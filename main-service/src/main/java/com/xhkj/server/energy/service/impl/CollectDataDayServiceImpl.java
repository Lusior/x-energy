package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.CollectDataDao;
import com.xhkj.server.energy.dao.CollectDataDayDao;
import com.xhkj.server.energy.dao.CompanyDao;
import com.xhkj.server.energy.dao.mybatis.vo.CollectData;
import com.xhkj.server.energy.dao.mybatis.vo.CollectDataDay;
import com.xhkj.server.energy.dao.mybatis.vo.Company;
import com.xhkj.server.energy.dao.mybatis.vo.StationData;
import com.xhkj.server.energy.entity.ReportDto;
import com.xhkj.server.energy.page.Page;
import com.xhkj.server.energy.service.CollectDataDayService;
import com.xhkj.server.energy.service.StationDataService;
import com.xhkj.server.energy.utils.DateUtils;
import com.xhkj.server.energy.utils.DateUtils2;
import com.xhkj.server.energy.utils.SortUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class CollectDataDayServiceImpl implements CollectDataDayService {

    @Autowired
    private CollectDataDayDao collectDataDayDao;

    @Autowired
    private CollectDataDao collectDataDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private StationDataService stationDataService;

    /**
     * @param num 0：今天，-1，昨天
     */
    public void dayDataCollect(int num) {
        String dayData = DateUtils.dayAdd(num, "yyyy-MM-dd");
        dataCollectDataHandler(dayData, dayData, dayData, CollectDataDay.DAY);
    }

    public void monthDataCollect(int year, int month) {
        Date date = new Date();
        if (year != 0 && month != 0) {
            date = DateUtils.toDate(year + "-" + month);
        }
        String firstDayOfMonth = DateUtils2.getFirstDayOfMonth(date);
        String lastDayOfMonth = DateUtils2.getLastDayOfMonth(date);
        dataCollectDataHandler(lastDayOfMonth, firstDayOfMonth, lastDayOfMonth, CollectDataDay.MONTH);
    }

    public void quarterDataCollect(int num) {
        String firstDayOfQuarter = DateUtils2.getFirstDayOfQuarter();
        String lastDayOfQuarter = DateUtils2.getLastDayOfQuarter();
        dataCollectDataHandler(lastDayOfQuarter, firstDayOfQuarter, lastDayOfQuarter, CollectDataDay.QUARTER);
    }

    public void yearDataCollect(int year) {
        String firstDayOfYear = DateUtils2.getCurrYearFirst();
        String lastDayOfYear = DateUtils2.getCurrYearLast();
        if (year != 0) {
            firstDayOfYear = year + "-01-31";
            lastDayOfYear = year + "-12-31";
        }
        dataCollectDataHandler(lastDayOfYear, firstDayOfYear, lastDayOfYear, CollectDataDay.YEAR);
    }

    private void dataCollectDataHandler(String keyDay, String beginTime, String endTime, int type) {
        List<Company> all = companyDao.findAll();
        for (Company company : all) {
            dataCollectDataHandler(keyDay, beginTime, endTime, type, company.getId());
        }
    }

    private void dataCollectDataHandler(String keyDay, String beginTime, String endTime, int type, int companyId) {
        if (!beginTime.contains(":")) {
            beginTime = beginTime + " 00:00:00";
        }
        if (!endTime.contains(":")) {
            endTime = endTime + " 23:59:59";
        }
        long time = DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm:ss").getTime();
        endTime = DateUtils.toString(new Date(time + 60 * 1000), "yyyy-MM-dd HH:mm:ss");
        List<CollectData> firstDataList = collectDataDao.getDataByOpTimeFirst(beginTime, endTime, companyId);
        List<CollectData> lastDataList = collectDataDao.getDataByOpTimeLast(beginTime, endTime, companyId);

//        for (CollectData collectData : lastDataList) {
//            System.out.println(collectData);
//        }
//        for (CollectData collectData : firstDataList) {
//            System.out.println(collectData);
//        }
        //3.进行数据计算
        String lastStationId = "";
        float ft3qTotal = 0f;
        float jqiTotal = 0f;
        float qqiTotal = 0f;
        for (CollectData collectData1 : lastDataList) {
            for (CollectData collectData2 : firstDataList) {
                if (collectData1.getStandId() != null && collectData1.getStandId().equals(collectData2.getStandId())) {
                    if (lastStationId.equals(collectData2.getStationId())) {// 不是站点的第一个机组，使用上次的结果
                        ft3qTotal = collectData1.getFt3q() - collectData2.getFt3q();// 水量按照机组统计，热、电只按照热站统计
                        break;
                    }
                    lastStationId = collectData2.getStationId();
                    ft3qTotal = collectData1.getFt3q() - collectData2.getFt3q();
                    jqiTotal = collectData1.getJqi() - collectData2.getJqi();
                    qqiTotal = collectData1.getQqi() - collectData2.getQqi();
                    break;
                }
            }
            CollectDataDay collectDataDay = new CollectDataDay();
            collectDataDay.setId(collectData1.getId());
            collectDataDay.setCompanyId(collectData1.getCompanyId());
            collectDataDay.setStandId(collectData1.getStandId());
            collectDataDay.setStationId(collectData1.getStationId());
            collectDataDay.setGroupId(collectData1.getGroupId());
            collectDataDay.setCvi1(collectData1.getCvi1());
            collectDataDay.setFc1v1(collectData1.getFc1v1());
            collectDataDay.setFt1(collectData1.getFt1());
            collectDataDay.setFt1q(collectData1.getFt1q());
            collectDataDay.setFt2(collectData1.getFt2());
            collectDataDay.setFt2q(collectData1.getFt2q());
            collectDataDay.setFt3(collectData1.getFt3());
            collectDataDay.setFt3q(collectData1.getFt3q());
            collectDataDay.setJqi(collectData1.getJqi());
            collectDataDay.setLt1(collectData1.getLt1());
            collectDataDay.setOpTime(collectData1.getOpTime());
            collectDataDay.setTe1(collectData1.getTe1());
            collectDataDay.setTe2(collectData1.getTe2());
            collectDataDay.setTe3(collectData1.getTe3());
            collectDataDay.setTe4(collectData1.getTe4());
            collectDataDay.setPt1(collectData1.getPt1());
            collectDataDay.setPt2(collectData1.getPt2());
            collectDataDay.setPt3(collectData1.getPt3());
            collectDataDay.setPt4(collectData1.getPt4());
            collectDataDay.setQi(collectData1.getQi());
            collectDataDay.setQqi(collectData1.getQqi());
            collectDataDay.setFt3qPrice(collectData1.getFt3qPrice());
            collectDataDay.setQqiPrice(collectData1.getQqiPrice());
            collectDataDay.setJqiPrice(collectData1.getJqiPrice());
            collectDataDay.setQqiTotal(qqiTotal);
            collectDataDay.setJqiTotal(jqiTotal);
            collectDataDay.setFt3qTotal(ft3qTotal);
            Date dt = DateUtils.parseDate(keyDay, "yyyy-MM-dd");
            collectDataDay.setDt(dt);
            collectDataDay.setType(type);
            List<CollectDataDay> byUniqueIndex = collectDataDayDao.getByUniqueIndex(type, dt, collectDataDay.getStandId(), companyId);
            if (byUniqueIndex != null && byUniqueIndex.size() > 0) {
                collectDataDay.setId(byUniqueIndex.get(0).getId());
                collectDataDayDao.update(collectDataDay);
            } else {
                //4.存入数据
                collectDataDayDao.save(collectDataDay);
            }
        }
    }

    /**
     * 水耗分析数据获取
     * todo 这三个方法需要整理一下
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getAnalysisWater(Page page) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足1位,会以0补足.
        //开始时间
        String beginTime = (String) page.getParams().get("beginTime");
        Date bDateTime = DateUtils.parseDate(beginTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        String endTime = (String) page.getParams().get("endTime");
        Date eDateTime = DateUtils.parseDate(endTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");

        page.getParams().put("dt", (String) page.getParams().get("endTime"));
        List<Map<String, Object>> listData = collectDataDayDao.getAnalysisWater(page);
        for (Map<String, Object> map : listData) {
            map.put("bDateTime", beginTime + " 00:00:00");
            //            map.put("eDateTime", DateUtils.toString((Date) map.get("op_time"), "yyyy-MM-dd HH:mm:ss"));
            map.put("eDateTime", DateUtils.toString(eDateTime, "yyyy-MM-dd HH:mm:ss"));
            float h = (float) ((eDateTime.getTime() - bDateTime.getTime()) / (1000.0 * 60.0 * 60.0)) + 24;
            map.put("hours", decimalFormat.format(h));
            double ft3q_total = (double) map.get("ft3q_total") * 1000;
            map.put("ft3q_total", ft3q_total);
            BigDecimal ft3q_total_b = new BigDecimal(String.valueOf(ft3q_total));
            BigDecimal real_area = new BigDecimal(String.valueOf(map.get("real_area")));
            String yxdhstr = "0";
            if (real_area.compareTo(BigDecimal.ZERO) == 1) {
                BigDecimal yxdh = ft3q_total_b.divide(real_area, 6, RoundingMode.HALF_UP);
                yxdhstr = decimalFormat.format(yxdh);
            }
            map.put("yxdhstr", yxdhstr);
            map.put("dt", map.get("dt").toString());
        }
        return SortUtils.sortListMap(listData, page);
    }

    /**
     * 电耗分析
     *
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> getAnalysisElectricity(Page page) {
        DecimalFormat decimalFormat1 = new DecimalFormat("0.0");
        DecimalFormat decimalFormat = new DecimalFormat("0.000000");//构造方法的字符格式这里如果小数不足1位,会以0补足.
        //开始时间
        String beginTime = (String) page.getParams().get("beginTime");
        Date bDateTime = DateUtils.parseDate(beginTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        String endTime = (String) page.getParams().get("endTime");
        Date eDateTime = DateUtils.parseDate(endTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");

        page.getParams().put("dt", (String) page.getParams().get("endTime"));
        List<Map<String, Object>> listData = collectDataDayDao.getAnalysisElectricity(page);
        for (Map<String, Object> map : listData) {
            map.put("bDateTime", beginTime + " 00:00:00");
            //            map.put("eDateTime", DateUtils.toString((Date) map.get("op_time"), "yyyy-MM-dd HH:mm:ss"));
            map.put("eDateTime", DateUtils.toString(eDateTime, "yyyy-MM-dd HH:mm:ss"));
            float h = (float) ((eDateTime.getTime() - bDateTime.getTime()) / (1000.0 * 60.0 * 60.0)) + 24;
            map.put("hours", decimalFormat1.format(h));
            BigDecimal jqi_total = new BigDecimal(String.valueOf(map.get("jqi_total")));
            BigDecimal real_area = new BigDecimal(String.valueOf(map.get("real_area")));
            String yxdhstr = "0";
            if (real_area.compareTo(BigDecimal.ZERO) == 1) {
                BigDecimal yxdh = jqi_total.divide(real_area, 6, RoundingMode.HALF_UP);
                yxdhstr = decimalFormat.format(yxdh);
            }
            map.put("yxdhstr", yxdhstr);
            map.put("dt", beginTime);
        }
        return SortUtils.sortListMap(listData, page);
    }

    /**
     * 热耗分析
     *
     * @param page
     * @return
     */
    @Override
    public List<Map<String, Object>> getAnalysisHeat(Page page) {
        DecimalFormat decimalFormat1 = new DecimalFormat("0.0");
        DecimalFormat decimalFormat = new DecimalFormat("0.000000");//构造方法的字符格式这里如果小数不足1位,会以0补足.
        //开始时间
        String beginTime = (String) page.getParams().get("beginTime");
        Date bDateTime = DateUtils.parseDate(beginTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        String endTime = (String) page.getParams().get("endTime");
        Date eDateTime = DateUtils.parseDate(endTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");

        page.getParams().put("dt", (String) page.getParams().get("endTime"));
        List<Map<String, Object>> listData = collectDataDayDao.getAnalysisHeat(page);
        for (Map<String, Object> map : listData) {
            map.put("bDateTime", beginTime + " 00:00:00");
//            map.put("eDateTime", DateUtils.toString((Date) map.get("op_time"), "yyyy-MM-dd HH:mm:ss"));
            map.put("eDateTime", DateUtils.toString(eDateTime, "yyyy-MM-dd HH:mm:ss"));
            float h = (float) ((eDateTime.getTime() - bDateTime.getTime()) / (1000.0 * 60.0 * 60.0)) + 24;
            map.put("hours", decimalFormat1.format(h));
            BigDecimal qqi_total = new BigDecimal(String.valueOf(map.get("qqi_total")));
            BigDecimal real_area = new BigDecimal(String.valueOf(map.get("real_area")));
            String yxdhstr = "0";
            if (real_area.compareTo(BigDecimal.ZERO) == 1) {
                BigDecimal yxdh = qqi_total.divide(real_area, 6, RoundingMode.HALF_UP);
                yxdhstr = decimalFormat.format(yxdh);
            }
            map.put("yxdhstr", yxdhstr);
            map.put("dt", beginTime);
        }
        return SortUtils.sortListMap(listData, page);
    }

    @Override
    public List<ReportDto> getDataByDateTime(Page<ReportDto> page) {
        int type = (int) page.getParams().get("type");
        Object oDt = page.getParams().get("dt");
        Date dt = null;
        if (oDt instanceof Date) {
            dt = (Date) oDt;
        } else if (oDt instanceof String) {
            dt = DateUtils.toDate(oDt.toString());
        }

        List<StationData> all = stationDataService.getAll();
        Map<String, StationData> stationDataMap = new TreeMap<>();
        for (StationData stationData : all) {
            stationDataMap.put(stationData.getStationId(), stationData);
        }

        Map<String, ReportDto> map = new TreeMap<>();

        List<CollectDataDay> byDateAndType = collectDataDayDao.getByDateAndType(type, dt, null);
        for (CollectDataDay data : byDateAndType) {
            ReportDto reportDto = map.get(data.getStationId());
            if (reportDto == null) {
                reportDto = new ReportDto(data);
                StationData stationData = stationDataMap.get(data.getStationId());
                if (stationData != null) {
                    reportDto.setStation_name(stationData.getStationName());
                }
                map.put(data.getStationId(), reportDto);
            } else {
                reportDto.setDay_ft3q_total(reportDto.getDay_ft3q_total() + data.getFt3qTotal());
                reportDto.setDay_ft3q(reportDto.getDay_ft3q() + data.getFt3q());
            }
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public List<Map<String, Object>> getDataEconByDateTime(Page page) {
        return collectDataDayDao.getDataEconByDateTime(page);
    }
}
