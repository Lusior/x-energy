package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.OprInfoLoginLogDao;
import com.xhkj.server.energy.dao.mybatis.vo.OprInfoLoginLog;
import com.xhkj.server.energy.service.OprInfoLoginLogService;
import com.xhkj.server.energy.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OprInfoLoginLogServiceImpl implements OprInfoLoginLogService {
    @Autowired
    OprInfoLoginLogDao oprInfoLoginLogDao;

    @Override
    public void writeloginLog(String loginId, String sessionId, String ip) {
        OprInfoLoginLog mercLoginLog = new OprInfoLoginLog();
        mercLoginLog.setIp(ip);
        mercLoginLog.setLoginId(loginId);
        mercLoginLog.setSessionId(sessionId);
        mercLoginLog.setLoginSts("01");
        mercLoginLog.setCrtDt(DateUtils.getYmdDate());
        mercLoginLog.setCrtTm(DateUtils.getHmsTime());
        oprInfoLoginLogDao.save(mercLoginLog);
    }

    @Override
    public Map<String, Object> getLoginLastDt(String loginId) {
        return oprInfoLoginLogDao.getLoginLastDt(loginId);
    }
}
