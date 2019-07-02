package com.xhkj.server.energy.task;

import com.xhkj.server.energy.service.impl.CollectDataDayServiceImpl;
import com.xhkj.server.energy.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SchedulerTask {

    private static final String pattern = "MM月dd日 HH时mm分ss秒";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CollectDataDayServiceImpl collectDataDayService;

    private void print(String prefix) {
        String s = DateUtils.toString(new Date(), pattern);
        System.out.println(prefix + ":" + s);
        logger.info(prefix + ":" + s);
    }

    @Scheduled(cron = "0 1 0/1 * * ?")
    public void hour() {
        print("hour");
    }

    @Scheduled(cron = "0 1 0 1/1 * ?")// 每天00点1分触发
    public void day() {
        print("day");
        collectDataDayService.dayDataCollect(-1);
        collectDataDayService.monthDataCollect(0, 0);
        collectDataDayService.quarterDataCollect(0);
        collectDataDayService.yearDataCollect(0);
    }

    @Scheduled(cron = "0 0 0 1 * ?")// 每月00点1分触发
    public void month() {
        print("month");
        collectDataDayService.monthDataCollect(2019, 2);
    }
}
