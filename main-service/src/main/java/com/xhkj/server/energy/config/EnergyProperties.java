package com.xhkj.server.energy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目配置
 */
@Component
@ConfigurationProperties(prefix = EnergyProperties.PREFIX)
public class EnergyProperties {

    static final String PREFIX = "energy";

    private String createFakeDataIp;
    private boolean runDataHandler;// 最好只有一台机器执行，否则可能会造成错误，之后优化

    public String getCreateFakeDataIp() {
        return createFakeDataIp;
    }

    public void setCreateFakeDataIp(String createFakeDataIp) {
        this.createFakeDataIp = createFakeDataIp;
    }

    public boolean isRunDataHandler() {
        return runDataHandler;
    }

    public void setRunDataHandler(boolean runDataHandler) {
        this.runDataHandler = runDataHandler;
    }
}
