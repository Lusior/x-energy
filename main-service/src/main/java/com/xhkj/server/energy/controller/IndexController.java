package com.xhkj.server.energy.controller;

import com.xhkj.server.energy.dao.mybatis.vo.*;
import com.xhkj.server.energy.entity.EnergyData;
import com.xhkj.server.energy.entity.MenuDto;
import com.xhkj.server.energy.service.*;
import com.xhkj.server.energy.shiro.ShiroUtil;
import com.xhkj.server.energy.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private StandDataService standDataService;
    @Autowired
    private StationDataService stationDataService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private CollectDataService collectDataService;

    @RequestMapping("/")
    public ModelAndView index(HttpSession session) {
        pageInitToSession(session);
        ModelAndView modelAndView = new ModelAndView("base/main");
        modelAndView.addObject("menus", getMenus());
        modelAndView.addObject("content", "home");
        return modelAndView;
    }

    @RequestMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        /* 初始化页面菜单数据 data */
        /* 这里取一下主页上需要的信息 **/
        //取一下机组信息
        List<StandData> standDataList = standDataService.getAll();
        Float ft3q = 0f;//水
        Float jqi = 0f;//电
        Float qqi = 0f;//热

        //接机组分组汇总
        List<CollectData> collectDataList = collectDataService.getETimeCollectDataList(DateUtils.getTodayNow(), 2);
        for (CollectData collectData : collectDataList) {
            ft3q += collectData.getFt3q();
            jqi += collectData.getJqi();
            qqi += collectData.getQqi();
        }

        Price price = priceService.getPriceData();

        Float totalquantity = ft3q + jqi + qqi;//水、电、热总数据
        Float totalAmount = ft3q * price.getFt3qPrice() + jqi * price.getJqiPrice() + qqi * price.getQqiPrice();//水、电、热总金额

        //取一下昨天的数据
        List<CollectData> collectDataYesList = collectDataService.getETimeCollectDataList(DateUtils.getTodayBegin(), 2);
        Float yesFt3q = 0f;
        Float yesJqi = 0f;
        Float yesQqi = 0f;//昨日热量
        for (CollectData collectData : collectDataYesList) {
            yesFt3q = collectData.getFt3q();
            yesJqi = collectData.getJqi();
            yesQqi += collectData.getQqi();

        }

        //取一下换热站信息
        List<StationData> stationDataList = stationDataService.getAll();

        int stationSize = 0;//换热站数量
        if (stationDataList != null && stationDataList.size() != 0) {
            stationSize = stationDataList.size();
        }
        int standArea = 0;
        for (StandData standData : standDataList) {
            Integer realArea = standData.getRealArea();
            standArea += realArea == null ? 0 : realArea;
        }
        modelAndView.addObject("ft3q", ft3q);
        modelAndView.addObject("jqi", jqi);
        modelAndView.addObject("qqi", qqi);
        modelAndView.addObject("yesFt3q", yesFt3q);
        modelAndView.addObject("yesJqi", yesJqi);
        modelAndView.addObject("yesQqi", yesQqi);
        modelAndView.addObject("priceData", price);
        modelAndView.addObject("collectDataList ", collectDataList);//基础数据
        modelAndView.addObject("totalquantity", totalquantity);//这是水、电、热总的数量
        modelAndView.addObject("totalAmount", totalAmount);//这是水、电、热总的金额
        modelAndView.addObject("standDataList", standDataList);//机组信息
        modelAndView.addObject("stationSize", stationSize);//换热站数量
        modelAndView.addObject("standArea", standArea);//机组总面积
        return modelAndView;
    }

    // 客户端用 todo 之后应该合并是上面的 home 接口
    @RequestMapping("/infoOverview")
    @ResponseBody
    public Map<String, Object> infoOverview(String beginTime, String endTime) {
        if (endTime == null || "".equals(endTime)) {
            endTime = DateUtils.getTodayNow();
        } else {
            endTime = DateUtils.getDayEnd(endTime);
        }
        //把按机组分的三耗放一个list里，这个客户端用来显示换热站的三耗。
        List<EnergyData> energyDataList = new ArrayList<>();

        // 接机组分组汇总。这个是总览数据
        List<CollectData> collectDataList = collectDataService.getETimeCollectDataList(endTime, 2);
        for (CollectData collectData : collectDataList) {
//            String standName = standService.getStandDataByStandId(collectData.getStandId()).getStandName();
            String stationName = stationDataService.getById(ShiroUtil.getCompanyId(), collectData.getStationId()).getStationName();

            EnergyData energyData = new EnergyData();
            energyData.setStationId(collectData.getStationId());
            energyData.setStationName(stationName);
            energyData.setStandId(collectData.getStandId());
//            energyData.setStandName(standName);
            energyData.setFt3q(String.valueOf(collectData.getFt3q()));
            energyData.setQqi(String.valueOf(collectData.getQqi()));
            energyData.setJqi(String.valueOf(collectData.getJqi()));
            energyDataList.add(energyData);
        }
        List<CollectData> beginlist = new ArrayList<>();
        //取一下开始日期的
        if (beginTime != null && !"".equals(beginTime)) {
            beginlist = collectDataService.getETimeCollectDataList(beginTime, 2);
        }

        Price priceData = priceService.getPriceData();

        //这里处理一下按机组分的3耗.就是用结束日期的三耗减去开始日期的三耗
        for (EnergyData energyData : energyDataList) {
            for (CollectData d : beginlist) {
                if (energyData.getStandId().equals(d.getStandId())) {
                    energyData.setFt3q(formatNum(Float.valueOf(energyData.getFt3q()) - d.getFt3q()));
                    energyData.setQqi(formatNum(Float.valueOf(energyData.getQqi()) - d.getQqi()));
                    energyData.setJqi(formatNum(Float.valueOf(energyData.getJqi()) - d.getJqi()));
                }
            }
        }
        // 合并机组数据为热站
        List<EnergyData> energyDataList2 = new ArrayList<>();
        EnergyData lastEnergyData = null;
        for (EnergyData energyData : energyDataList) {
            if (lastEnergyData == null || !lastEnergyData.getStationId().equals(energyData.getStationId())) {
                lastEnergyData = energyData;
                energyDataList2.add(lastEnergyData);
            } else {
                lastEnergyData.setFt3q(Float.parseFloat(lastEnergyData.getFt3q()) + Float.parseFloat(energyData.getFt3q()) + "");
            }
        }
        float ft3qAll = 0;
        float jqiAll = 0;
        float qqiAll = 0;
        for (EnergyData energyData : energyDataList2) {
            ft3qAll += Float.parseFloat(energyData.getFt3q());
            jqiAll += Float.parseFloat(energyData.getJqi());
            qqiAll += Float.parseFloat(energyData.getQqi());
        }

        Map<String, Object> retMap = new HashMap<>();
        retMap.put("ft3q", formatNum(ft3qAll));//保留两位
        retMap.put("jqi", formatNum(jqiAll));
        retMap.put("qqi", formatNum(qqiAll));
        float ft3qPrice = ft3qAll * priceData.getFt3qPrice();
        float qqiPrice = qqiAll * priceData.getQqiPrice();
        float jqiPrice = jqiAll * priceData.getJqiPrice();
        retMap.put("ft3q_a", formatNum(ft3qPrice));
        retMap.put("jqi_a", formatNum(jqiPrice));
        retMap.put("qqi_a", formatNum(qqiPrice));
        retMap.put("ft3q_price", priceData.getFt3qPrice());
        retMap.put("jqi_price", priceData.getJqiPrice());
        retMap.put("qqi_price", priceData.getQqiPrice());
        retMap.put("total_price", formatNum(ft3qPrice + jqiPrice + qqiPrice));
        retMap.put("energyDataList", energyDataList2);

        return retMap;
    }

    //取两位小数 给客户端显示用
    private String formatNum(Float f) {
        DecimalFormat fnum = new DecimalFormat("##0.00");
        return fnum.format(f);
    }

    protected List<MenuDto> getMenus() {
        List<Menu> menus = menuService.getAll();
        // 配置前端页面的menu显示
        menus.remove(0);

        List<MenuDto> results = new ArrayList<>();
        MenuDto lastNoLeafMenuDto = null;
        for (Menu menu : menus) {
            MenuDto menuDto = new MenuDto(menu, menuIsLeaf(menu));
            if (menuIsLeaf(menu)) {
                if (lastNoLeafMenuDto == null) {// 没有父节点，直接是子节点
                    results.add(menuDto);
                } else {
                    lastNoLeafMenuDto.addSubMenu(menuDto);
                }
            } else {
                lastNoLeafMenuDto = menuDto;
                results.add(lastNoLeafMenuDto);
            }
        }
        return results;
    }

    private boolean menuIsLeaf(Menu menu) {
        return !menu.getMenuPid().trim().equals("1000") || menu.getMenuCode().trim().equals("home");
    }
}
