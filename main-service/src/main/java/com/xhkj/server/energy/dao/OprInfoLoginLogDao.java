package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.OprInfoLoginLog;

import java.util.Map;

public interface OprInfoLoginLogDao extends Dao<OprInfoLoginLog, Long> {

    ////*******自定义开始********//
    Map<String, Object> getLoginLastDt(String loginId);
    //**********自定义结束*****////

}
