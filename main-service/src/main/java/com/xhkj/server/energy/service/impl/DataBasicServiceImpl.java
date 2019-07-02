package com.xhkj.server.energy.service.impl;

import com.xhkj.server.energy.dao.DataBasicDao;
import com.xhkj.server.energy.dao.mybatis.vo.DataBasic;
import com.xhkj.server.energy.service.DataBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataBasicServiceImpl implements DataBasicService {

    @Autowired
    private DataBasicDao dataBasicDao;

    @Override
    public List<DataBasic> getNoProcessedList(int count) {
        return dataBasicDao.getNoProcessedList(count);
    }

    @Override
    public DataBasic getLastLegal(int company, String standId) {
        return dataBasicDao.getLastLegal(company, standId);
    }

    @Transactional
    @Override
    public void updateProcessedStatus(DataBasic dataBasic) {
        dataBasic.setProcessed(true);
        dataBasicDao.update(dataBasic);
    }
}
