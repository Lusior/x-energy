package com.xhkj.server.energy.service;

import com.xhkj.server.energy.entity.ReportDto;
import com.xhkj.server.energy.page.Page;

import java.util.List;
import java.util.Map;

public interface CollectDataDayService {

    List<Map<String, Object>> getAnalysisWater(Page page);

    List<Map<String, Object>> getAnalysisElectricity(Page page);

    List<Map<String, Object>> getAnalysisHeat(Page page);

    List<ReportDto> getDataByDateTime(Page<ReportDto> page);

    List<Map<String, Object>> getDataEconByDateTime(Page page);
//    int getDataByDateTimeCount(Page<CollectDataDay> page);
}
