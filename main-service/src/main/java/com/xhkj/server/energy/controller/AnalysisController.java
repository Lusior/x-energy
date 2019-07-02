package com.xhkj.server.energy.controller;

import com.xhkj.server.energy.dao.mybatis.vo.CollectDataDay;
import com.xhkj.server.energy.page.Page;
import com.xhkj.server.energy.service.CollectDataDayService;
import com.xhkj.server.energy.utils.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/basic/analysis")
@Controller
public class AnalysisController extends BaseController {

    @Autowired
    private CollectDataDayService collectDataDayService;

    @RequiresPermissions("heat_station_water:view")
    @RequestMapping("/water")
    public ModelAndView toWater() {
        ModelAndView modelAndView = new ModelAndView("analysis/water");
        String yesterday = "'" + DateUtils.dayAdd(-1, "yyyy-MM-dd") + "'";
        modelAndView.addObject("defaultDate", yesterday);
        return modelAndView;
    }

    @RequiresPermissions("heat_station_water:view")
    @RequestMapping("/water/list")
    @ResponseBody
    public Page<Map<String, Object>> waterList(Page<Map<String, Object>> page) {
        initPage(page);
        List<Map<String, Object>> analysisWaterList = collectDataDayService.getAnalysisWater(page);
        page.setRows(analysisWaterList);
        page.setTotal(analysisWaterList.size());
        return page;
    }

    @RequiresPermissions("heat_station_water:view")
    @RequestMapping("/water/list/export")
    public void waterListExport(String beginTime, String endTime, String stationName, Page<CollectDataDay> page) {
        page.getParams().put("beginTime", beginTime);
        page.getParams().put("endTime", endTime);
        page.getParams().put("stationName", stationName);
        page.setLimit(9999999);//因为需要下载全部页数据
        page.setOffset(0);
        List<Map<String, Object>> allList = collectDataDayService.getAnalysisWater(page);
        String fileName = "水耗分析" + DateUtils.getYmdHmsDate() + ".xls";
//        exportFile(response, allList, "analysis_water", fileName, endTime);
    }

    @RequiresPermissions("heat_station_electricity:view")
    @RequestMapping("/electricity")
    public ModelAndView toElectricity() {
        ModelAndView modelAndView = new ModelAndView("analysis/electricity");
        modelAndView.addObject("defaultDate", "'" + DateUtils.dayAdd(-1, "yyyy-MM-dd") + "'");
        return modelAndView;
    }

    @RequiresPermissions("heat_station_electricity:view")
    @RequestMapping("/electricity/list")
    @ResponseBody
    public Page<Map<String, Object>> electricityList(Page<Map<String, Object>> page) {
        initPage(page);
        List<Map<String, Object>> analysisElectricityList = collectDataDayService.getAnalysisElectricity(page);
        page.setRows(analysisElectricityList);
        page.setTotal(analysisElectricityList.size());
        return page;
    }

    @RequiresPermissions("heat_station_electricity:view")
    @RequestMapping("/electricity/list/export")
    public void electricityListExport(String beginTime, String endTime, String stationName, Page<CollectDataDay> page) {
        page.getParams().put("beginTime", beginTime);
        page.getParams().put("endTime", endTime);
        page.getParams().put("stationName", stationName);
        page.setLimit(9999999);//因为需要下载全部页数据
        page.setOffset(0);
        List<Map<String, Object>> allList = collectDataDayService.getAnalysisElectricity(page);
        String fileName = "电耗分析" + DateUtils.getYmdHmsDate() + ".xls";
//        exportFile(response, allList, "analysis_electricity", fileName, endTime);
    }

    @RequiresPermissions("heat_station_heat:view")
    @RequestMapping("/heat")
    public ModelAndView toHeat(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("analysis/heat");
        modelAndView.addObject("defaultDate", "'" + DateUtils.dayAdd(-1, "yyyy-MM-dd") + "'");
        return modelAndView;
    }

    @RequiresPermissions("heat_station_heat:view")
    @RequestMapping("/heat/list")
    @ResponseBody
    public Page<Map<String, Object>> heatList(Page<Map<String, Object>> page) {
        initPage(page);
        List<Map<String, Object>> analysisHeatList = collectDataDayService.getAnalysisHeat(page);
        page.setRows(analysisHeatList);
        page.setTotal(analysisHeatList.size());
        return page;
    }

    @RequiresPermissions("heat_station_heat:view")
    @RequestMapping("/heat/list/export")
    public void heatListExport(HttpServletRequest request, HttpServletResponse response, String beginTime, String endTime, String stationName, Page<CollectDataDay> page) {
        page.getParams().put("beginTime", beginTime);
        page.getParams().put("endTime", endTime);
        page.getParams().put("stationName", stationName);
        page.setLimit(9999999);//因为需要下载全部页数据
        page.setOffset(0);
        List<Map<String, Object>> allList = collectDataDayService.getAnalysisHeat(page);
        String fileName = "热耗分析" + DateUtils.getYmdHmsDate() + ".xls";
//        exportFile(response, allList, "analysis_heat", fileName, endTime);
    }
}
