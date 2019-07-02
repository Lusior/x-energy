package com.xhkj.server.energy.controller;

import com.xhkj.server.energy.dao.TecolCurveCulDataDao;
import com.xhkj.server.energy.dao.ValveCompanyDao;
import com.xhkj.server.energy.dao.mybatis.vo.Valve;
import com.xhkj.server.energy.dao.mybatis.vo.ValveCompany;
import com.xhkj.server.energy.service.TecolCurveService;
import com.xhkj.server.energy.service.ValveService;
import com.xhkj.server.energy.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/tecolcurve")
public class TecolCurveController extends BaseController<Valve> {

    @Autowired
    private ValveService valveService;
    @Autowired
    private ValveCompanyDao valveCompanyDao;
    @Autowired
    private TecolCurveService tecolCurveService;
    @Autowired
    private TecolCurveCulDataDao tecolCurveCulDataDao;
    @Autowired
    private WeatherService weatherService;

    //@RequiresPermissions()
    @ResponseBody
    @GetMapping("/runningConclusion") //运行结论
    public ModelAndView actualData() {
        List<ValveCompany> all = valveCompanyDao.getAll();
        ModelAndView modelAndView = new ModelAndView("valve/actual_data");
        modelAndView.addObject("projects", all);
        modelAndView.addObject("tecolCurveData", tecolCurveService.getTecolCurveData());
        modelAndView.addObject("tecolCurveCulData", tecolCurveCulDataDao.getAll());
        modelAndView.addObject("curTem", weatherService.getWeatherStalCurrent().getTem());
        return modelAndView;
    }

}
