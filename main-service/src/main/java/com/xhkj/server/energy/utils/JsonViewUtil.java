package com.xhkj.server.energy.utils;

import com.alibaba.fastjson.JSON;
import com.xhkj.server.energy.page.BootstrapTablePage;
import com.xhkj.server.energy.page.BootstrapTableParams;
import com.xhkj.server.energy.page.Page;
import com.xhkj.server.energy.returnvalue.ReturnUtil;
import com.xhkj.server.energy.returnvalue.ReturnValue;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JsonViewUtil {

    public static void handle(String viewName, ModelMap model) {
        if ("/json".equals(viewName) || "json".equals(viewName)) {
            if (model.get("returnValue") == null) {
                ReturnValue<Object> xReturnValue = convertToReturnValue(model, null);
                model.put("returnValue", xReturnValue);
            }
            model.put("json", JSON.toJSONString(model.get("returnValue")));
        }
    }

    public static ReturnValue<Object> convertToReturnValue(ModelMap model, Object defaultValue) {
        ReturnValue<Object> xReturnValue = (ReturnValue<Object>) ReturnUtil.creatSuccessful();
        Map<String, Object> value = new HashMap<>();
        if (!model.isEmpty()) {
            for (Map.Entry<String, Object> entry : model.entrySet()) {
                if (isNeedInclude(entry.getKey())) {
                    value.put(entry.getKey(), entry.getValue());
                }
            }
        }
        ModelAndView modelAndView = (ModelAndView) model.get("modelAndView");
        if (modelAndView != null) {
            for (Map.Entry<String, Object> entry : modelAndView.getModelMap().entrySet()) {
                if (isNeedInclude(entry.getKey())) {
                    value.put(entry.getKey(), entry.getValue());
                }
            }
        }
        if (value.size() == 0) {
            if (defaultValue instanceof Page) {
                Page p = ((Page) defaultValue);
                xReturnValue.setValue(p.getRows() != null ? p.getRows() : p.getData());
            } else if (defaultValue instanceof ModelAndView) {
                for (Map.Entry<String, Object> entry : ((ModelAndView) defaultValue).getModelMap().entrySet()) {
                    if (isNeedInclude(entry.getKey())) {
                        value.put(entry.getKey(), entry.getValue());
                    }
                }
                xReturnValue.setValue(value);
            } else {
                xReturnValue.setValue(defaultValue);
            }
        } else if (value.size() == 1) {
            // todo 单值的情况，为了去掉一层包装，（这个或者可以根据指定特殊key的方式做）
            Object value1 = value.values().toArray()[0];
            if (value1 instanceof Page) {
                Page p = ((Page) value1);
                xReturnValue.setValue(p.getRows() != null ? p.getRows() : p.getData());
            } else if (value1 instanceof BootstrapTableParams) {
                xReturnValue.setValue(((BootstrapTablePage) defaultValue).getRows());
            } else if (value1 instanceof BootstrapTablePage) {
                xReturnValue.setValue(((BootstrapTablePage) value1).getRows());
            } else {
                xReturnValue.setValue(value1);
            }
        } else {
            xReturnValue.setValue(value);
        }
        return xReturnValue;
    }

    private static Set<String> excludedSet = new HashSet<String>() {
        {
            add("modelAndView");
        }
    };

    private static boolean isNeedInclude(String key) {
        for (String excluded : excludedSet) {
            if (excluded.equalsIgnoreCase(key)) {
                return false;
            }
        }
        return !key.startsWith("org.springframework");
    }
}
