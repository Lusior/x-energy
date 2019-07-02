package com.xhkj.server.energy.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    private static Log log = LogFactory.getLog(RequestUtils.class);

    public static String extractURI(HttpServletRequest request) {
        String requestURI = request.getRequestURI().replaceAll(";.+$", "");
        String contextPath = request.getContextPath();
        if ("/".equals(contextPath) || "".equals(contextPath)) {
            return requestURI.replace(".html", "");
        }
        return requestURI.replace(contextPath, "").replace(".html", "");
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        Object reference = requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        return (HttpServletRequest) reference;
    }

    public static String getContextPath() {
        return getRequest().getContextPath();
    }

    public static String getRequestIp() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        Object reference = requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        if (reference instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) reference;
            try {
                String xForwardedForIp = request.getHeader("X-Forwarded-For");
                if (Strings.isNotBlank(xForwardedForIp)) {
                    return xForwardedForIp.split(",")[0].trim();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String ip = request.getHeader("X-Real-IP");
            if (Strings.isBlank(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        }
        return "";
    }

    public static Boolean isAjaxRequest() {
        HttpServletRequest request = getRequest();
        if ("true".equalsIgnoreCase(request.getParameter("ajax"))) {
            return true;
        }
        boolean ajax = "true".equalsIgnoreCase(request.getHeader("ajax"));
//        log.info("isAjaxRequest=" + ajax);
        return ajax;
    }

}
