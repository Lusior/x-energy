package com.xhkj.server.energy.dao.mybatis.impl;

import com.xhkj.server.energy.dao.CollectDataDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectDataDaoImplTest {
    @Autowired
    CollectDataDao collectDataDao;

    @Test
    public void get() {
        System.out.println(collectDataDao.get(35883));
    }
}