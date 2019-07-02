package com.xhkj.server.energy.controller;

import com.xhkj.server.energy.dao.mybatis.vo.StandData;
import com.xhkj.server.energy.dao.mybatis.vo.StationData;
import com.xhkj.server.energy.service.StandDataService;
import com.xhkj.server.energy.service.StationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/basic/stand")
@Controller
public class StandController {

    @Autowired
    private StationDataService stationDataService;
    @Autowired
    private StandDataService standDataService;

    @RequestMapping("/list")
//    @ResponseBody
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("stand/stand");
        List<StationData> stationDataList = stationDataService.getAll();
        List<StandData> standDataList = standDataService.getAll();
        modelAndView.addObject("stationDataList", stationDataList);
        modelAndView.addObject("standDataList", standDataList);
        return modelAndView;
    }
}
