package com.xhkj.server.energy.service;

import com.xhkj.server.energy.dao.mybatis.vo.CollectData;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CollectDataService {

    List<CollectData> getETimeCollectDataList(String endTime, int groupType);

    List<CollectData> getByTimeRangeAndStandId(Date startTime, Date endTime, String standId);

    void save(CollectData collectData);

    /**
     * 获取最大批次号
     */
    Integer getMaxBatchNumber();

    /**
     * 获取站的实时网络信息
     */
    List<Map<String, Object>> getStationRtNwData(@Param("maxBatchNumber") Integer maxBatchNumber);
}
