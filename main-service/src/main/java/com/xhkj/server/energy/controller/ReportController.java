package com.xhkj.server.energy.controller;

import com.xhkj.server.energy.dao.mybatis.vo.CollectDataDay;
import com.xhkj.server.energy.entity.ReportDto;
import com.xhkj.server.energy.exception.MyExceptionBuilder;
import com.xhkj.server.energy.exception.MyExceptionEnum;
import com.xhkj.server.energy.page.Page;
import com.xhkj.server.energy.service.CollectDataDayService;
import com.xhkj.server.energy.utils.DateUtils;
import com.xhkj.server.energy.utils.DateUtils2;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/report")
public class ReportController extends BaseController {

    @Autowired
    private CollectDataDayService collectDataDayService;

    @RequiresPermissions("report_day:view")
    @RequestMapping("/day")
    public ModelAndView dayIndex(HttpSession session) {
        pageInitToSession(session);
        return new ModelAndView("report/report_day");
    }

    @RequiresPermissions("report_month:view")
    @RequestMapping("/month")
    public ModelAndView monthIndex(HttpSession session) {
        pageInitToSession(session);
        return new ModelAndView("report/report_month");
    }

    @RequiresPermissions("report_quarter:view")
    @RequestMapping("/quarter")
    public ModelAndView quarterIndex(HttpSession session) {
        pageInitToSession(session);
        return new ModelAndView("report/report_quarter");
    }

    @RequiresPermissions("report_year:view")
    @RequestMapping("/year")
    public ModelAndView yearIndex(HttpSession session) {
        pageInitToSession(session);
        return new ModelAndView("report/report_year");
    }

    @RequiresPermissions("report_economic:view")
    @RequestMapping("/econ")
    public ModelAndView econIndex(HttpSession session) {
        pageInitToSession(session);
        return new ModelAndView("report/report_econ");
    }

    /**
     * 返回日报表
     *
     * @param page
     * @return
     */
    @RequiresPermissions("report_day:view")
    @RequestMapping("/day/list")
    @ResponseBody
    public Page<ReportDto> dayList(Page<ReportDto> page, HttpServletRequest request) {
        initPage(page);
        String searchDateTime = getParamByName(page, request, "searchDateTime");
        page.getParams().put("dt", DateUtils.toDate(searchDateTime));
        setPageData(page, CollectDataDay.DAY);
        return page;
    }

    private String getParamByName(Page<?> page, HttpServletRequest request, String name) {
        String parameter = request.getParameter(name);
        if (Strings.isNotBlank(parameter)) {
            return parameter;
        }
        parameter = (String) page.getParams().get(name);
        if (Strings.isBlank(parameter)) {
            throw MyExceptionBuilder.newBuilder(MyExceptionEnum.PARAMETER_ERROR).build();
        }
        return parameter;
    }

    private void setPageData(Page<ReportDto> page, int type) {
        page.getParams().put("type", type);
        List<ReportDto> dataByDateTime = collectDataDayService.getDataByDateTime(page);
        page.setRows(dataByDateTime);
        page.setTotal(dataByDateTime.size());
    }

    /**
     * 返回日报表导出
     *
     * @param page
     * @return
     */
    @RequiresPermissions("report_day:view")
    @RequestMapping("/day/list/export")
    public void dayListExport(Page<CollectDataDay> page) {
        initPage(page);
        String searchDateTime = getParamByName(page, request, "searchDateTime");
        page.getParams().put("dt", DateUtils.toDate(searchDateTime));
        page.getParams().put("type", CollectDataDay.DAY);
//        List<Map<String, Object>> retList = collectDataDayService.getDataByDateTime(page);
        String fileName = "日报表" + DateUtils.getYmdHmsDate() + ".xls";
//        exportFile(response, retList, "report_date", fileName, searchDateTime);
    }

    /**
     * 返回月报表
     *
     * @param page
     * @return
     */
    @RequiresPermissions("report_month:view")
    @RequestMapping("/month/list")
    @ResponseBody
    public Page<ReportDto> monthList(Page<ReportDto> page) {
        initPage(page);
        String searchDateTime = getParamByName(page, request, "searchDateTime");
        Date date = DateUtils.toDate(searchDateTime);
        String lastDayOfMonth = DateUtils2.getLastDayOfMonth(date);
        page.getParams().put("dt", DateUtils.toDate(lastDayOfMonth));
        setPageData(page, CollectDataDay.MONTH);
        return page;
    }

    /**
     * 返回月报表
     *
     * @param page
     * @return
     */
    @RequiresPermissions("report_month:view")
    @RequestMapping("/month/list/export")
    public void monthListExport(Page<CollectDataDay> page) {
        initPage(page);
        String searchDateTime = getParamByName(page, request, "searchDateTime");
        Date date = DateUtils.toDate(searchDateTime);
        String lastDayOfMonth = DateUtils2.getLastDayOfMonth(date);
        page.getParams().put("dt", DateUtils.toDate(lastDayOfMonth));
        page.getParams().put("type", CollectDataDay.MONTH);
//        List<Map<String, Object>> retList = collectDataDayService.getDataByDateTime(page);
        String fileName = "月报表" + DateUtils.getYmdHmsDate() + ".xls";
//        exportFile(response, retList, "report_date", fileName, searchDateTime);
    }

    /**
     * 返回年报表
     *
     * @param page
     * @return
     */
    @RequiresPermissions("report_year:view")
    @RequestMapping("/year/list")
    @ResponseBody
    public Page<ReportDto> yearList(Page<ReportDto> page) {
        initPage(page);
        String year = getParamByName(page, request, "year");
//        String stationName = getParamByName(page, request, "stationName");
        page.getParams().put("dt", year + "-12-31");
        setPageData(page, CollectDataDay.YEAR);
        return page;
    }

    /**
     * 导出年报表
     *
     * @param page
     * @return
     */
    @RequiresPermissions("report_year:view")
    @RequestMapping("/year/list/export")
    public void yearListExport(Page<CollectDataDay> page) {
        initPage(page);
        String year = getParamByName(page, request, "year");
//        String stationName = getParamByName(page, request, "stationName");
        page.getParams().put("dt", year + "-12-31");
        page.getParams().put("type", CollectDataDay.YEAR);
//        List<Map<String, Object>> retList = collectDataDayService.getDataByDateTime(page);
        String fileName = "年报表" + DateUtils.getYmdHmsDate() + ".xls";
//        exportFile(response, retList, "report_date", fileName, year + "年");
    }

    /**
     * 返回季度报表
     *
     * @param page
     * @return
     */
    @RequiresPermissions("report_quarter:view")
    @RequestMapping("/quarter/list")
    @ResponseBody
    public Page<ReportDto> quarterList(Page<ReportDto> page) {
        initPage(page);
        String quarter = getParamByName(page, request, "quarter");
//        String stationName = getParamByName(page, request, "stationName");
        String dt = DateUtils2.getCurrQuarter(Integer.parseInt(quarter))[1];
        page.getParams().put("dt", dt);
        setPageData(page, CollectDataDay.QUARTER);
        return page;
    }

    /**
     * 导出季度报表
     *
     * @param page
     * @return
     */
    @RequiresPermissions("report_quarter:view")
    @RequestMapping("/quarter/list/export")
    public void quarterListExport(Page<CollectDataDay> page) {
        initPage(page);
        String quarter = getParamByName(page, request, "quarter");
//        String stationName = getParamByName(page, request, "stationName");
        String dt = DateUtils2.getCurrQuarter(Integer.parseInt(quarter))[1];
        page.getParams().put("dt", dt);
        page.getParams().put("type", CollectDataDay.QUARTER);
//        List<Map<String, Object>> retList = collectDataDayService.getDataByDateTime(page);
        String fileName = "季度报表" + DateUtils.getYmdHmsDate() + ".xls";
//        exportFile(response, retList, "report_date", fileName, year + "年" + quarter + "季度");
    }

    /**
     * 返经济分析报表
     *
     * @param page
     * @return
     */
    @RequiresPermissions("report_economic:view")
    @RequestMapping("/econ/list")
    @ResponseBody
    public Page<Map<String, Object>> econList(Page<Map<String, Object>> page) {
        initPage(page);
        String beginTime = getParamByName(page, request, "beginTime");
        String endTime = getParamByName(page, request, "endTime");
//        String stationName = (String) page.getParams().get("stationName");
//        Date dt = collectDataDayService.getMonthEconMaxDay(beginTime, endTime, stationName);
//        if (dt == null) {
//            return page;
//        }
        page.getParams().put("type", CollectDataDay.DAY);
        page.getParams().put("beginDt", beginTime);
        page.getParams().put("endDt", endTime);
        List<Map<String, Object>> dataEconList = collectDataDayService.getDataEconByDateTime(page);
        page.setRows(dataEconList);
        page.setTotal(dataEconList.size());
        return page;
    }

    @RequiresPermissions("report_economic:view")
    @RequestMapping("/econ/list/export")
    public void econListExport(String beginTime, String endTime, String stationName, Page<CollectDataDay> page) {
//        page.getParams().put("beginTime", beginTime);
//        page.getParams().put("endTime", endTime);
//        page.getParams().put("stationName", stationName);
//        Date dt = collectDataDayService.getMonthEconMaxDay(beginTime, endTime, stationName);
//        if (dt == null) {
//            return;
//        }
        page.getParams().put("type", CollectDataDay.DAY);
        page.getParams().put("beginDt", beginTime);
        page.getParams().put("endDt", endTime);
        page.setLimit(9999999);//因为需要下载全部页数据
        page.setOffset(0);
        List<Map<String, Object>> allList = collectDataDayService.getDataEconByDateTime(page);
        String fileName = "经济分析报表" + DateUtils.getYmdHmsDate() + ".xls";
//        exportFile(response, allList, "report_econ", fileName, endTime);
    }

}
