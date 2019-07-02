package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.CollectDataDay;
import com.xhkj.server.energy.page.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CollectDataDayDao extends Dao<CollectDataDay, Integer> {

    ////*******自定义开始********//

    List<CollectDataDay> getByUniqueIndex(int type, Date dt, String standId, int companyId);

    List<Map<String, Object>> getAnalysisWater(Page page);

    List<Map<String, Object>> getAnalysisElectricity(Page page);

    List<Map<String, Object>> getAnalysisHeat(Page page);

    float getTotalHeat(Date yesterdayDate);

    List<CollectDataDay> getByDateAndType(int type, Date dt, List<String> stationIds);

    List<Map<String, Object>> getDataEconByDateTime(Page page);

    //**********自定义结束*****////

}
