package com.xhkj.server.energy.controller;

import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.entity.OprInfoDto;
import com.xhkj.server.energy.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class LoginController {

    @RequestMapping(value = "/login")
    public String showLoginForm(HttpServletRequest req, Model model) {
        String error = null;
        String userName = req.getParameter("loginId");
        String password = req.getParameter("loginPwd");
        if (Strings.isBlank(userName) || Strings.isBlank(password)) {
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(false);

        try {
            subject.login(token);
        } catch (UnknownAccountException u) {
            error = "用户名不存在";
            System.out.println(error);
        } catch (LockedAccountException l) {
            //用户被锁定，例如管理员把某个用户禁用...
            error = "密码错误次数过多,账号被锁定10分钟,稍后再试.";
            System.out.println(error);
        } catch (ConcurrentAccessException c) {
            //登录异常
            error = "用户名错误";
            System.out.println(error);
        } catch (AuthenticationException i) {
            error = "用户名或者密码错误";
            System.out.println(error);
        }
        if (error != null) {
            model.addAttribute("error", error);
        }
        OprInfo oprInfo = (OprInfo) subject.getPrincipal();
        model.addAttribute(new OprInfoDto(oprInfo, subject.getSession().getId().toString()));
        return "redirect:/";
    }

    @RequestMapping(value = "/login/info")
    @ResponseBody
    public Map<String, Object> loginInfo() {
        OprInfo oprInfo = (OprInfo) SecurityUtils.getSubject().getPrincipal();
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("loginId", oprInfo.getLoginId());
        retMap.put("oprName", oprInfo.getOprName());
        retMap.put("phone", oprInfo.getPhone());
        retMap.put("email", oprInfo.getEmail());
        retMap.put("token", SecurityUtils.getSubject().getSession().getId());
        String lastdt = oprInfo.getLastDt();
        String lasttm = oprInfo.getLastTm();
        if (StringUtils.isNotEmpty(lastdt) && StringUtils.isNotEmpty(lasttm)) {
            retMap.put("lastDtTm", DateUtils.transferDtTm(oprInfo.getLastDt() + oprInfo.getLastTm()));
        }
        return retMap;
    }

    @RequestMapping("/exit")
    public ModelAndView exit(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            if (subject.getSession() != null && subject.getPrincipal() != null) {
                //退出销毁
                SecurityUtils.getSubject().logout();
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("redirect:/");
    }
}
