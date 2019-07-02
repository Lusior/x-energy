package com.xhkj.server.energy.entity;

import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.utils.BeanUtils;

public class OprInfoDto extends OprInfo {

    private String token;

    public OprInfoDto(OprInfo oprInfo, String token) {
        BeanUtils.copyProperties(oprInfo, this, true);
        this.setToken(token);
        this.setLoginPwd(null);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
