package com.xhkj.server.energy.service;

import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;
import com.xhkj.server.energy.page.BootstrapTableParams;

import java.util.List;

public interface OprInfoService {
    Boolean save(OprInfo oprInfo);

    OprInfo getById(String oprId);

    Boolean modify(OprInfo oprInfo);

    Boolean del(OprInfo oprInfo);

    Boolean isExistOprInfo(String loginId);

    Boolean modifyPwd(OprInfo oprInfo);

    Boolean modifyPassword(String oldPassword, String newPassword);

    OprInfo getByLoginId(String loginId);

    List<OprInfo> getAll(BootstrapTableParams params);
}
