package com.xhkj.server.energy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xhkj.server.energy.config.EnergyProperties;
import com.xhkj.server.energy.dao.ValveDao;
import com.xhkj.server.energy.dao.mybatis.vo.Valve;
import com.xhkj.server.energy.entity.ValveResponse;
import com.xhkj.server.energy.service.ValveService;
import com.xhkj.server.energy.utils.DateUtils;
import com.xhkj.server.energy.utils.HIPUtil;
import com.xhkj.server.energy.utils.HttpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ValveServiceImpl implements ValveService {

    private static final String pattern = "MM月dd日 HH时mm分ss秒";

    private static final String LOGIN_URL = "http://devices.tiger-control.com:8080/login?login=jiaoshoujiayuan1&password=yu58bk";
    private static final String PROJECT_URL = "http://devices.tiger-control.com:8080/projects?token=%s";
    private static final String VALVE_URL = "http://devices.tiger-control.com:8080/project/%s/valves?token=%s&type=0";
    private static final String UPDATE_URL = "http://devices.tiger-control.com:8080/valve/%s/open?value=%s&token=%s";

    private HttpService httpService = new HttpService();

    @Autowired
    private EnergyProperties energyProperties;
    @Autowired
    ValveDao valveDao;

    @Override
    public List<Valve> getAllLastBatchNumber(String projectId) {
        return valveDao.getAllLastBatchNumber(projectId);
    }

    @Override
    public List<Valve> getAllLastBatchNumberForTime(String projectId, String searchTime) {
        return valveDao.getAllLastBatchNumberForTime(projectId, searchTime);
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void getData() {
        String createFakeDataIp = energyProperties.getCreateFakeDataIp();
        String ip = HIPUtil.get(true);
        boolean equals = createFakeDataIp.equals(ip);
        print("ValveServiceGetData ip = " + ip + ", execute = " + equals);
        if (equals) {
            realGetData();
        }
    }

    private String token = null;
    private int batchNumber = 0;

    void realGetData() {
        String s = DateUtils.toString(new Date(), "yyMMddHHmm");
        batchNumber = Integer.parseInt(s);
        getToken();
        getProject();
    }

    private void getValve(String projectId) {
        String s = httpService.doGet(String.format(VALVE_URL, projectId, token));
        ValveResponse<List<JSONObject>> valveResponse = JSON.parseObject(s, ValveResponse.class);
        for (JSONObject object : valveResponse.getData()) {
            Valve valve = JSONObject.parseObject(object.toJSONString(), Valve.class);
            valve.setProjectId(projectId);
            valve.setBatchNumber(batchNumber);
            valveDao.save(valve);
        }
    }

    private void getProject() {
        String s = httpService.doGet(String.format(PROJECT_URL, token));
        ValveResponse<List<JSONObject>> valveResponse = JSON.parseObject(s, ValveResponse.class);
        List<JSONObject> list = valveResponse.getData();
        for (JSONObject jsonObject : list) {
            String projectId = jsonObject.getString("id");
            getValve(projectId);
        }
    }

    private void getToken() {
        String s = httpService.doPost(LOGIN_URL, "");
        ValveResponse<JSONObject> valveResponse = JSON.parseObject(s, ValveResponse.class);
        this.token = valveResponse.getData().getString("token");
    }

    private void print(String prefix) {
        String s = DateUtils.toString(new Date(), pattern);
        System.out.println(prefix + ":" + s);
    }
}
