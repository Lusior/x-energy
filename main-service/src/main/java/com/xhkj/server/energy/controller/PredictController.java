package com.xhkj.server.energy.controller;

import com.xhkj.server.energy.dao.mybatis.vo.PredictData;
import com.xhkj.server.energy.service.PredictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/predict")
@Controller
public class PredictController {

    @Autowired
    PredictService predictService;

    //负荷预测修改
    @RequiresPermissions("predict:view")
    @RequestMapping("/update")
//    @ResponseBody
    public ModelAndView update() {
        ModelAndView modelAndView = new ModelAndView("predict/predict");
        PredictData thePredict = predictService.getThePredict();
        System.out.println(thePredict);
        modelAndView.addObject("thePredict", thePredict);
        return modelAndView;
    }

    //负荷预测修改
//    @RequestMapping("/updateData")
    @ResponseBody
    public Boolean updateData(@RequestParam Float q, @RequestParam Float Tn) {
        PredictData thePredict = new PredictData();
        thePredict.setQ(q);
        thePredict.setTn(Tn);
        return predictService.updateHaErBinSelective(thePredict);
    }
}
