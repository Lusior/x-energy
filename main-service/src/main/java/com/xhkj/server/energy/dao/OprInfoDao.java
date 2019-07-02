package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.page.BootstrapTableParams;

import java.util.List;
import java.util.Map;

public interface OprInfoDao extends Dao<OprInfo, Integer> {

    ////*******自定义开始********//
    void deleteOprRoleRel(Map<String, Object> delRoleMap);

    void insertOprRoleRel(List<Map<String, Object>> roleList);

    int getCountByLoginId(String loginId);

    int updatePwdByOprId(OprInfo oprInfo);

    int modifyPassword(Map<String, Object> paramMap);

    OprInfo getByLoginId(String loginId);

    List<OprInfo> getAll(BootstrapTableParams params);

    OprInfo getById(int parseInt);
    //**********自定义结束*****////

}
