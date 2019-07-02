package com.xhkj.server.energy;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@MapperScan("com.xhkj.server.energy.dao.mybatis.mapper")
@EnableTransactionManagement
@SpringBootApplication
@EnableConfigurationProperties
public class XEnergyApplication {

    private static Logger logger = LoggerFactory.getLogger(XEnergyApplication.class);

    @Value("${spring.profiles.active}")
    String activeProfile;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(XEnergyApplication.class, args);
        logger.info("当前激活 profile=" + applicationContext.getEnvironment().getProperty("spring.profiles.active"));
    }

}
