package com.xhkj.server.energy.controller;

import com.xhkj.server.energy.dao.mybatis.vo.Price;
import com.xhkj.server.energy.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    private PriceService priceService;

    @ResponseBody
    @GetMapping("/ok")
    public String ok() {
        System.out.println(activeProfile);
        return "ok";
    }

    @GetMapping("/test")
    public String success(Map<String, Object> params) {
        params.put("hello", "<h2>你好</h2>");
        List<String> list = Arrays.asList("zhansan", "lisi", "wangwu");
        params.put("users", list);
        return "index_test";
    }

    @ResponseBody
    @GetMapping("/dao")
    public Price dao(int id) {
        return priceService.getById(id);
    }

    @ResponseBody
    @GetMapping("/daoAll")
    public List<Price> daoAll() {
        return priceService.getAll();
    }
}
