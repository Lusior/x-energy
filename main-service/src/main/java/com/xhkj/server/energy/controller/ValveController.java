package com.xhkj.server.energy.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xhkj.server.energy.dao.ValveCompanyDao;
import com.xhkj.server.energy.dao.mybatis.vo.Valve;
import com.xhkj.server.energy.dao.mybatis.vo.ValveCompany;
import com.xhkj.server.energy.page.BootstrapTablePage;
import com.xhkj.server.energy.page.BootstrapTableParams;
import com.xhkj.server.energy.service.ValveService;
import com.xhkj.server.energy.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/valve")
public class ValveController extends BaseController<Valve> {

    @Autowired
    private ValveService valveService;
    @Autowired
    private ValveCompanyDao valveCompanyDao;

    @GetMapping("/actualData")
    public ModelAndView actualData() {
        List<ValveCompany> all = valveCompanyDao.getAll();
        ModelAndView modelAndView = new ModelAndView("valve/actual_data");
        modelAndView.addObject("projects", all);
        return modelAndView;
    }

    @GetMapping("/historyData")
    public ModelAndView historyData() {
        List<ValveCompany> all = valveCompanyDao.getAll();
        ModelAndView modelAndView = new ModelAndView("valve/history_data");
        modelAndView.addObject("projects", all);
        return modelAndView;
    }

    @GetMapping("/historyCurve")
    public ModelAndView historyCurve() {
        return new ModelAndView("valve/history_curve");
    }

    @ResponseBody
    @PostMapping("/actualDataAjax")
    public BootstrapTablePage<Valve> actualDataAjax(BootstrapTableParams params) {
        initParams(params);
        PageHelper.startPage(params.getPageNum(), params.getLimit());
        String projectId = params.getParams().get("projectId");
        List<Valve> getAll = valveService.getAllLastBatchNumber(projectId);
        return new BootstrapTablePage<>(getAll);
    }

    @ResponseBody
    @PostMapping("/historyDataAjax")
    public BootstrapTablePage<Valve> historyDataAjax(BootstrapTableParams params) {
        initParams(params);
        PageHelper.startPage(params.getPageNum(), params.getLimit());
        String projectId = params.getParams().get("projectId");
        String searchTime = params.getParams().get("tTime") + ":00";
        String tTime = DateUtils.toString(DateUtils.toDate(searchTime), "yyMMddHHmm");
        List<Valve> getAll = valveService.getAllLastBatchNumberForTime(projectId,tTime);
        return new BootstrapTablePage<>(getAll);
    }
}
