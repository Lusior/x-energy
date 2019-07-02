package com.xhkj.server.energy.shiro;

import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.exception.MyExceptionBuilder;
import com.xhkj.server.energy.exception.MyExceptionEnum;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {

    public static int getCompanyId() {
        return getOprInfo().getCompanyId();
    }

    public static OprInfo getOprInfo() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof OprInfo) {
            return (OprInfo) principal;
        }
        throw MyExceptionBuilder.newBuilder(MyExceptionEnum.NO_LOGIN).build();
    }
}
