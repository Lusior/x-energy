package com.xhkj.server.energy.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xhkj.server.energy.dao.mybatis.vo.Company;
import com.xhkj.server.energy.dao.mybatis.vo.Menu;
import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.dao.mybatis.vo.WeatherData;
import com.xhkj.server.energy.entity.BaseEntity;
import com.xhkj.server.energy.page.BootstrapTableParams;
import com.xhkj.server.energy.page.Page;
import com.xhkj.server.energy.service.CompanyService;
import com.xhkj.server.energy.service.MenuService;
import com.xhkj.server.energy.service.WeatherService;
import com.xhkj.server.energy.shiro.ShiroUtil;
import com.xhkj.server.energy.utils.DateUtils;
import com.xhkj.server.energy.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseController<T extends BaseEntity> extends AbstractController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    private MenuService menuService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private CompanyService companyService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    void initParams(BootstrapTableParams page) {
        String line;
        try (BufferedReader in = request.getReader()) {
            //读流
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            //json转Map
            Map<String, String> params = new Gson().fromJson(sb.toString(), new TypeToken<Map<String, String>>() {
            }.getType());
            //初始化参数
            if (params != null) {
                if (params.get("limit") != null && params.get("offset") != null) {
                    int limit = (Integer.parseInt(params.get("limit")));
                    int offset = (Integer.parseInt(params.get("offset")));
                    page.setLimit(limit);
                    page.setOffset(offset);
                    page.setPageNum(offset / limit + 1);
                }
                if (params.get("sort") != null && params.get("sortOrder") != null) {
                    page.setSort(params.get("sort"));
                    page.setOrder(params.get("order"));
                }
                page.setParams(params);
                // 当前操作员登录机构
                //page.getParams().put("currOrgId", getCurrOprInfo().getOrgId());
            }
        } catch (IOException e) {
            logger.error("读取参数异常", e);
        }
        // 填充参数
        if (page.getParams() == null) {
            page.setParams(new HashMap<>());
        }
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            page.getParams().put(s, request.getParameter(s));
        }
        page.getParams().put("companyId", ShiroUtil.getCompanyId() + "");
    }

    public void initPage(Page<T> page, T entity) {
        page.setEntity(entity);
    }

    public void initPage(Page<T> page) {
        String line;
        try (BufferedReader in = request.getReader()) {
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            Map<String, Object> params = StringUtils.getGson().fromJson(sb.toString(), new TypeToken<Map<String, Object>>() {
            }.getType());
            if (params != null) {
                if (params.get("limit") != null && params.get("offset") != null) {
                    page.setLimit((int) (Double.parseDouble(params.get("limit").toString())));
                    page.setOffset((int) (Double.parseDouble(params.get("offset").toString())));
                }
                if (params.get("sort") != null && params.get("sortOrder") != null) {
                    page.setSort(params.get("sort").toString());
                    page.setSortOrder(params.get("sortOrder").toString());
                }
                page.setParams(params);
                // 当前操作员登录机构
                page.getParams().put("currOrgId", getCurrOprInfo().getOrgId());
            }
        } catch (IOException e) {
            logger.error("读取参数异常", e);
        }
        // 填充参数
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            page.getParams().put(s, request.getParameter(s));
        }
        page.getParams().put("companyId", ShiroUtil.getCompanyId());
    }

    protected void pageInitToSession(HttpSession session) {
        menuAddSession(session);
        weatherAddSession(session);
        iconAndTitleToSession(session);
    }

    private void iconAndTitleToSession(HttpSession session) {
        Company company = companyService.getCompany();
        String icon = company.getIcon();
        String title = company.getTitle();
        session.setAttribute("COMPANY_ICON", icon);
        session.setAttribute("COMPANY_TITLE", title);
    }

    public OprInfo getCurrOprInfo() {
        return (OprInfo) SecurityUtils.getSubject().getPrincipal();
    }

    public void initEntity(T entity) {
        initCrt(entity);
        initUpt(entity);
    }

    public void initCrt(T entity) {
        entity.setCrtDt(DateUtils.getYmdDate());
        entity.setCrtTm(DateUtils.getHmsTime());
    }

    public void initUpt(T entity) {
        entity.setUptDt(DateUtils.getYmdDate());
        entity.setUptTm(DateUtils.getHmsTime());
    }

    private void menuAddSession(HttpSession session) {
        // 从session中获取菜单展示
        String menuHtml = (String) session.getAttribute("menuHtml");
        if (menuHtml == null || menuHtml.length() < 1) {
            OprInfo oprInfo = getCurrOprInfo();
            String main_url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            /* 获取登陆菜单 */
            List<Menu> menus = menuService.getMenus(oprInfo.getRoles());
            // 配置前端页面的menu显示
            menus.remove(0);
            StringBuilder sb = new StringBuilder();
            int i = 0;
            // 判断首页显示
            Menu firstMenu = menus.get(0);
            if (firstMenu != null && "home".equals(firstMenu.getMenuCode())) {
                sb.append("<li id='home'><a href='").append(main_url).append(firstMenu.getMenuUrl()).append("'><i class='fa fa-home'></i >").append(firstMenu.getMenuName()).append("</a></li>");
                i = 1;
            }
            for (; i < menus.size(); i++) {
                Menu menu = menus.get(i);
                if (!menuIsLeaf(menu)) { // 根节点
                    sb.append(" <li > ");
                    sb.append(firstLevelMenu(menu));
                    sb.append(" <ul class='submenu'> ");
                } else if (menuIsLeaf(menu)) {// 叶节点
                    sb.append(secondLevelMenu(menu, main_url));
                    if (i + 1 == menus.size() || !menuIsLeaf(menus.get(i + 1))) { // 判断是否为当前叶节点的结尾
                        sb.append("</ul></li>");
                    }
                }
            }
            menuHtml = sb.toString();
            session.setAttribute("menuHtml", menuHtml);
        }
    }

    private String firstLevelMenu(Menu menu) {
        return "<a href=\"#\" class=\"dropdown-toggle\">\n" +
                "    <i class=\"" + menu.getIcon() + " normal\"></i>\n" +
                "    <span class=\"menu-text normal\"> " + menu.getMenuName() + " </span>\n" +
                "    <b class=\"arrow fa fa-angle-right normal\"></b>\n" +
                "</a>";
    }

    private String secondLevelMenu(Menu menu, String baseUrl) {
        return "<li><a href=\"" + baseUrl + menu.getMenuUrl() + "\">\n" +
                //"    <i class=\"fa fa-caret-right\"></i>\n" +  //这里的向右一个箭头，去掉了，双层向右箭头 fa-angle-double-right
                "    <i class=\"fa\"></i>\n" +
                "    <span class=\"menu-text\">" + menu.getMenuName() + "</span>\n" +
                "</a></li>";
    }

    //获取天气信息
    public void weatherAddSession(HttpSession session) {
        String weatherHtml = (String) session.getAttribute("weatherHtml");
        if (weatherHtml == null || weatherHtml.length() < 1) {
            //获取当前天气状况
            WeatherData weatherData = weatherService.findWeatherCurr();
            if (weatherData != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("<b>今日:</b> ").append(DateUtils.getDayWeek());
                sb.append("&nbsp;&nbsp;哈尔滨  ").append(weatherData.getTeC1());
                sb.append("&nbsp;&nbsp;<font color='red'>").append(weatherData.getTeH()).append("℃</font>~ <font color='blue'>").append(weatherData.getTeL()).append("℃ </font>");
                sb.append(weatherData.getTeWd1()).append("&nbsp;&nbsp;").append(weatherData.getTeWe1());
                session.setAttribute("weatherHtml", sb.toString());
            }
        }
    }

    private boolean menuIsLeaf(Menu menu) {
        return !menu.getMenuPid().trim().equals("1000") || menu.getMenuCode().trim().equals("home");
    }
}
