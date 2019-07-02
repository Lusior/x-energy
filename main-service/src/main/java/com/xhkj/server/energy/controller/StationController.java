package com.xhkj.server.energy.controller;

import com.xhkj.server.energy.dao.StandBasicDataDao;
import com.xhkj.server.energy.dao.mybatis.vo.CollectData;
import com.xhkj.server.energy.dao.mybatis.vo.StandBasicData;
import com.xhkj.server.energy.dao.mybatis.vo.StandData;
import com.xhkj.server.energy.dao.mybatis.vo.StationData;
import com.xhkj.server.energy.entity.CollectExtendData;
import com.xhkj.server.energy.page.Page;
import com.xhkj.server.energy.service.CollectDataService;
import com.xhkj.server.energy.service.PriceService;
import com.xhkj.server.energy.service.StandDataService;
import com.xhkj.server.energy.service.StationDataService;
import com.xhkj.server.energy.shiro.ShiroUtil;
import com.xhkj.server.energy.utils.DateUtils;
import com.xhkj.server.energy.utils.MapAndBeanUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/basic/station")
@Controller
public class StationController extends BaseController {

    @Autowired
    private StationDataService stationDataService;
    @Autowired
    private StandDataService standDataService;
    @Autowired
    private CollectDataService collectDataService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private StandBasicDataDao standBasicDataDao;

    //换热站列表
    @RequiresPermissions("basic_station_list:view")
    @RequestMapping("/list")
//    @ResponseBody
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("station/station");
        List<StationData> stationDataList = stationDataService.getAll();
        List<StandData> standDataList = standDataService.getAll();
        modelAndView.addObject("stationDataList", stationDataList);
        modelAndView.addObject("standDataList", standDataList);
        // 下面两项客户端用
        modelAndView.addObject("priceData", priceService.getPriceData());
        modelAndView.addObject("standBasicDataList", standBasicDataDao.getByCompany(ShiroUtil.getCompanyId()));
        return modelAndView;
    }

    //换热站页面上table编辑
    @RequestMapping("/updateInfoWithTable")
//    @ResponseBody
    public void updatePrice(@RequestParam boolean stationFlag,
                            @RequestParam int inOrder,
                            @RequestParam String tId,
                            @RequestParam String tValue) {
        try {
            if (stationFlag) {//如果修改的是换热站信息
                StationData stationData = stationDataService.getById(ShiroUtil.getCompanyId(), tId);
                switch (inOrder) {
                    case 1:
                        stationData.setPersonnel(tValue);//如果修改的是负责人
                        break;
                    case 2:
                        stationData.setTelephone(tValue);
                    case 3:
                        stationData.setAddress(tValue);
                    case 4:
                        stationData.setCoordinate(tValue);
                    default:
                        break;
                }
                stationDataService.update(stationData);
            } else {
                StandData standData = standDataService.getById(ShiroUtil.getCompanyId(), tId);
                if (inOrder == 1) {
                    standData.setDesignArea(Integer.parseInt(tValue == null ? "0" : tValue));
                } else {
                    standData.setRealArea(Integer.parseInt(tValue == null ? "0" : tValue));
                }
                standDataService.update(standData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //换热站相关信息/实时数据
//    @RequiresPermissions("station:actualdata")
    @RequestMapping("/actualdata")
    public ModelAndView actualData() {
        return new ModelAndView("station/actualdata");
    }

    //    @RequiresPermissions("station:actualdata")
    @RequestMapping("/actualdataAjax")
    @ResponseBody
    public Page<CollectExtendData> actualDataAjax() {
        //接机组分组汇总
        List<CollectExtendData> collectExtendData = addCollectExtendActualData(DateUtils.getTodayNow(), 2);
        Page<CollectExtendData> page = new Page<>();
        //接机组分组汇总
        page.setData(collectExtendData);
        return page;
    }

    @RequestMapping("/actualdataAjax/export")
    public void actualDataExport(HttpServletResponse response) {
        List<Map<String, Object>> retList = MapAndBeanUtil.objectsToMaps(addCollectExtendActualData(DateUtils.getTodayNow(), 2));
        String fileName = "瞬时数据" + DateUtils.getYmdHmsDate() + ".xls";
        exportFile(response, retList, "actualdata", fileName, null);
    }

    //换热站相关信息/历史数据
//    @RequiresPermissions("station:historydata")
    @RequestMapping("/historydata")
    public ModelAndView historyData() {
        return new ModelAndView("station/historydata");
    }

    //    @RequiresPermissions("station:actualdata")
    @RequestMapping("/historydataAjax")
    @ResponseBody
    public Page<CollectExtendData> historydata(HttpServletRequest request) {
        String tTime = request.getParameter("tTime") + ":00";
        Page page = new Page();
        page.setData(addCollectExtendActualData(tTime, 2));
        return page;
    }

    @RequestMapping("/historydataAjax/export")
    public void historyDataExport(HttpServletRequest request, HttpServletResponse response) {
        String tTime = request.getParameter("tTime") + ":00";
        List<Map<String, Object>> retList = MapAndBeanUtil.objectsToMaps(addCollectExtendActualData(tTime, 2));
        String fileName = "历史数据" + DateUtils.getYmdHmsDate() + ".xls";
        exportFile(response, retList, "actualdata", fileName, tTime);
    }

    //换热站相关信息/历史曲线
//    @RequiresPermissions("station:historycurve")
    @RequestMapping("/historycurve")
    public ModelAndView historyCurve() {
        ModelAndView modelAndView = new ModelAndView("station/historycurve");
        List<StationData> stationDataList = stationDataService.getAll();
        List<StandData> standDataList = standDataService.getAll();
        modelAndView.addObject("stationDataList", stationDataList);
        modelAndView.addObject("standDataList", standDataList);
        return modelAndView;
    }

    //换热站相关信息/历史曲线
//    @RequiresPermissions("station:actualdata")
    @RequestMapping("/historycurveAjax")
    @ResponseBody
    public List<CollectExtendData> historyCurveAjax(HttpServletRequest request) {
        String standId = request.getParameter("standSelect");
        String bTime = request.getParameter("tTime") + ":00";
        int timeRange = Integer.parseInt(request.getParameter("timeRange"));//时间范围
        String timeType = request.getParameter("timeType");
        String eTime = DateUtils.getTodayNow();
        if ("minute".equals(timeType)) {
            eTime = DateUtils.fixMinute(bTime, timeRange);
        } else if ("hour".equals(timeType)) {
            eTime = DateUtils.fixHour(bTime, timeRange);
        } else if ("day".equals(timeType)) {
            eTime = DateUtils.fixDay(bTime, timeRange);
        }
        List<CollectData> collectDataList = collectDataService.getByTimeRangeAndStandId(DateUtils.toDate(bTime), DateUtils.toDate(eTime), standId);
        List<CollectExtendData> collectExtendDataList = new ArrayList<>();
        for (CollectData collectData : collectDataList) {
            collectExtendDataList.add(new CollectExtendData(collectData));
        }
        return collectExtendDataList;
    }

    //换热站相关信息/一网排序
//    @RequiresPermissions("station:onenetwork")
    @RequestMapping("/onenetwork")
    public ModelAndView onenetwork(HttpServletRequest request) {
        return new ModelAndView("station/onenetwork");
    }

    //换热站相关信息/一网排序
//    @RequiresPermissions("station:twonetwork")
    @RequestMapping("/twonetwork")
    public ModelAndView twonetwork(HttpServletRequest request) {
        return new ModelAndView("station/twonetwork");
    }

    //    @RequiresPermissions("station:actualdata")
    @RequestMapping("/netWorkAjax")
    @ResponseBody
    public Page<CollectExtendData> oneNetWorkAjax(HttpServletRequest request, HttpSession session) {
        String tTime = request.getParameter("tTime") + ":00";
        int curveSelect = Integer.parseInt(request.getParameter("curveSelect"));
        int groupType = 2;//接机组分组汇总
        if (curveSelect == 1 || curveSelect == 2) {//3或4是二网排序
            groupType = 1;//接换热站分组汇总
        }
        List<CollectExtendData> collectExtendData = addCollectExtendActualData(tTime, groupType);
        Page page = new Page();
        page.setData(collectExtendData);
        return page;
    }

    //组装数据用的
    private List<CollectExtendData> addCollectExtendActualData(String endTime, int groupType) {
        List<CollectExtendData> collectExtendDataList = new ArrayList<>();
        //取一下机组信息
        List<StandData> standDataList = standDataService.getAll();
        List<CollectData> collectDataList = collectDataService.getETimeCollectDataList(endTime, groupType);
        //取一下换热站信息
        List<StationData> stationDataList = stationDataService.getAll();
        for (CollectData collectData : collectDataList) {
            CollectExtendData collectExtendData = new CollectExtendData(collectData);
            for (StandData standData : standDataList) {
                if (collectData.getStandId().equals(standData.getStandId())) {
                    collectExtendData.setStandName(standData.getStandName());
                    break;
                }
            }
            for (StationData stationData : stationDataList) {
                if (collectData.getStationId().equals(stationData.getStationId())) {
                    collectExtendData.setStationName(stationData.getStationName());
                    break;
                }
            }
            collectExtendDataList.add(collectExtendData);
        }
        return collectExtendDataList;
    }

    /**
     * 换热站信息全网分布使用
     */
    @RequestMapping("/all/network")
    public ModelAndView allNetwork(HttpServletRequest request, HttpSession session) {
        return new ModelAndView("station/all_network");
    }

    /**
     * 换热站信息全网分布中-获取站的实时信息
     */
    @RequestMapping("/all/network/rtNw")
    @ResponseBody
    public List<Map<String, Object>> allNetworkRtNwData(HttpServletRequest request, HttpSession session) {
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        //最大批次号
        Integer maxBatchNumber = collectDataService.getMaxBatchNumber();
        if (null == maxBatchNumber || 0 == maxBatchNumber) {
            return retList;
        }
        return collectDataService.getStationRtNwData(maxBatchNumber);
    }

    /**
     * @param response
     * @param exportList     导出的数据
     * @param excelModelName 与excel_model中的对应的modelId名称
     * @param fileName       生成的文件名称
     * @param sheetName      生成sheet名称
     */
    protected void exportFile(HttpServletResponse response, List<Map<String, Object>> exportList, String excelModelName, String fileName, String sheetName) {
//        ExcelTemplateType excelTemplate = ExcelXmlModelFactory.getInstance().getExcelInfo(excelModelName);
//        // 2.创建excel工具对象
//        ExcelExportUtil excelExport = new ExcelExportUtil(excelTemplate);
//        try {
//            // 3.根据模版创建HSSFWorkbook对象
//            excelExport.createWorkBookByTemplate(sheetName);
//            excelExport.writeQueryResult(exportList);
//            HSSFWorkbook hSSFWorkbook = excelExport.getHSSFWorkbook();
//            DownloadFileUtil.getInstance().downLoadExcel(hSSFWorkbook, fileName, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
