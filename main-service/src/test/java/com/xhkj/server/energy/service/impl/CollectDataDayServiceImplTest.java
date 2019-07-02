package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.mybatis.vo.CollectDataDay;
import com.xhkj.server.energy.page.Page;
import com.xhkj.server.energy.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectDataDayServiceImplTest {

    @Autowired
    CollectDataDayServiceImpl collectDataDayService;

    @Test
    public void testDataCollect() {
        collectDataDayService.dayDataCollect(-1);
        collectDataDayService.monthDataCollect(0, 0);
        collectDataDayService.quarterDataCollect(0);
        collectDataDayService.yearDataCollect(0);
    }

    @Test
    public void analysisWater() {
        Page<CollectDataDay> page = new Page<>();
        page.getParams().put("beginTime", "2017-06-16");
        page.getParams().put("endTime", "2017-06-17");
        collectDataDayService.getAnalysisWater(page);
    }

    public static void main(String[] args) {
        String dayBegin = DateUtils.dayAdd(0, "yyyyMMdd") + "000000";
        String dayEnd = DateUtils.dayAdd(0, "yyyyMMdd") + "235959";
        Date date1 = DateUtils.toDate(dayBegin);
        Date date2 = DateUtils.toDate(dayEnd);
        System.out.println(dayBegin);
        System.out.println(dayEnd);
        int m0 = 36827 + 689;
        int m1 = 37511 + 717;
        int m2 = 38346 + 756;
        int m3 = 38617 + 781;
        System.out.println(m1 - m0);
        System.out.println(m2 - m1);
        System.out.println(m3 - m2);
        System.out.println(m3 - m0);
    }
}