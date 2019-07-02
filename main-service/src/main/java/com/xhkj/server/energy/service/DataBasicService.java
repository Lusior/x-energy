package com.xhkj.server.energy.service;

import com.xhkj.server.energy.dao.mybatis.vo.DataBasic;

import java.util.List;

public interface DataBasicService {

    List<DataBasic> getNoProcessedList(int count);

    DataBasic getLastLegal(int company, String standId);

    void updateProcessedStatus(DataBasic dataBasic);
}
