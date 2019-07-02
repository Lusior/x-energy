package com.xhkj.server.energy.controller;

import com.xhkj.server.energy.dao.mybatis.vo.Price;
import com.xhkj.server.energy.service.PriceService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/basic/price")
@Controller
public class PriceController {

    @Autowired
    private PriceService priceService;

    //价格列表
    @RequestMapping("/list")
//    @ResponseBody
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("price/price");
        Price price = priceService.getPriceData();
        modelAndView.addObject("priceData", price);
        return modelAndView;
    }

    @RequiresPermissions("basic_price_update:view")
    @RequestMapping("/updatePrice")
    @ResponseBody
    public boolean updatePrice(@RequestParam String companyId, @RequestParam float ft3qPrice, @RequestParam float jqiPrice, @RequestParam float qqiPrice) {
        Price price = null;
        if (!StringUtils.isEmpty(companyId)) {
            price = priceService.get(Integer.parseInt(companyId));
        }
        if (price != null) {
            price.setFt3qPrice(ft3qPrice);
            price.setJqiPrice(jqiPrice);
            price.setQqiPrice(qqiPrice);
            return priceService.updatePrice(price);
        } else {
            price = new Price();
            price.setFt3qPrice(ft3qPrice);
            price.setJqiPrice(jqiPrice);
            price.setQqiPrice(qqiPrice);
            return priceService.insertPrice(price);
        }
    }
}