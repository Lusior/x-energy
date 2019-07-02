package com.xhkj.server.energy.service;

import java.util.Map;

public interface OprInfoLoginLogService {
    void writeloginLog(String loginId, String toString, String ip);

    Map<String, Object> getLoginLastDt(String loginId);
}
