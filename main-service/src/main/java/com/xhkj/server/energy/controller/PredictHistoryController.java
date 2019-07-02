package com.xhkj.server.energy.controller;

import com.xhkj.server.energy.dao.PredictStationDao;
import com.xhkj.server.energy.dao.mybatis.vo.PredictData;
import com.xhkj.server.energy.dao.mybatis.vo.PredictHistoryData;
import com.xhkj.server.energy.page.Page;
import com.xhkj.server.energy.service.PredictHistoryService;
import com.xhkj.server.energy.service.PredictService;
import com.xhkj.server.energy.utils.DateUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/predict/history")
@Controller
public class PredictHistoryController extends BaseController {

    @Autowired
    PredictHistoryService predictHistoryService;
    @Autowired
    PredictService predictService;
    @Autowired
    PredictStationDao predictStationDao;

    //负荷预测历史
    @RequiresPermissions("predict_history:view")
    @RequestMapping("/list")
//    @ResponseBody
    public ModelAndView list() {
        return new ModelAndView("predict/predict_history_data");
    }

    //负荷预测历史
    @SuppressWarnings("unchecked")
    @RequiresPermissions("predict_history:view")
    @RequestMapping("/history_data")
//    @ResponseBody
    public Page<PredictHistoryData> historyData(Page<PredictHistoryData> page) {
        initPage(page);
        page.setData(predictHistoryService.getPredictHistoryDatas(page));
        return page;
    }

    //负荷预测历史
    @RequestMapping("/history_data2")
    @ResponseBody
    @CrossOrigin
    public Map<String, String> historyData(String beginTime) {
        Date targetDate = DateUtils.toDate(beginTime);
        Date lastDate = DateUtils.dayAdd(-1, targetDate);
        PredictData thePredict = predictService.getThePredict();
        PredictHistoryData targetData = predictHistoryService.getByOpTime(targetDate);
        PredictHistoryData lastData = predictHistoryService.getByOpTime(lastDate);

        Map<String, String> map = new HashMap<>();
        map.put("cityName", thePredict.getCityName());
        map.put("L", thePredict.getL().toString());
        map.put("Tw1", thePredict.getTw1().toString());
        map.put("Tpj1", thePredict.getTpj1().toString());
        map.put("N", thePredict.getN().toString());
        map.put("F", thePredict.getF().toString());
        map.put("k", thePredict.getQ().toString());
        map.put("Tn", thePredict.getTn().toString());
        map.put("Q1", thePredict.getQ1().toString());
        if (targetData != null) {
            map.put("Tpj2", targetData.getTpj2().toString());
            map.put("Tpj3", targetData.getTpj3().toString());
            map.put("Tpj4", targetData.getTpj4().toString());
            map.put("Tpj5", targetData.getTpj5().toString());
            map.put("Q3", targetData.getQ3().toString());
            map.put("Q4", targetData.getQ4().toString());
            map.put("Q5", targetData.getQ5().toString());
            map.put("Q6", targetData.getQ6().toString());
            map.put("k1", targetData.getK1().toString());
            map.put("k2", targetData.getK2().toString());
            map.put("Q2", targetData.getQ2().toString());
            if (lastData != null) {
                map.put("yesQ3", lastData.getQ3().toString());
            } else {
                map.put("yesQ3", targetData.getQ2().toString());
            }
        }
        return map;
    }

    //负荷预测历史
    @RequestMapping("/history_data3")
    @ResponseBody
    @CrossOrigin
    public Map<String, Object> historyData3(String beginTime) {
        Date targetDate = DateUtils.toDate(beginTime);
        Date lastDate = DateUtils.dayAdd(-1, targetDate);
        PredictData thePredict = predictService.getThePredict();
        PredictHistoryData targetData = predictHistoryService.getByOpTime(targetDate);
        PredictHistoryData lastData = predictHistoryService.getByOpTime(lastDate);
        Map<String, Object> map = new HashMap<>();
        map.put("baseData", thePredict);
        if (targetData != null) {
            map.put("targetData", targetData);
            if (lastData != null) {
                map.put("yesQ3", lastData.getQ3().toString());
            } else {
                map.put("yesQ3", targetData.getQ2().toString());
            }
        }
        map.put("predictStationList", predictStationDao.getByDate(targetDate));
        map.put("predictStationListLast", predictStationDao.getByDate(lastDate));
        return map;
    }
}
