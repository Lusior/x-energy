package com.xhkj.server.energy.dao;

import com.xhkj.server.energy.dao.mybatis.vo.CollectData;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CollectDataDao extends Dao<CollectData, Integer> {

    ////*******自定义开始********//

    List<CollectData> getETimeCollectDataList(String endTime, int groupType);

    List<CollectData> getByTimeRangeAndStandId(Date startTime, Date endTime, String standId);

    /**
     * 报表使用 --查询每个时间段最后一条机组信息
     */
    List<CollectData> getDataByOpTimeLast(String beginTime, String endTime, int companyId);

    List<CollectData> getDataByOpTimeFirst(String beginTime, String endTime, int companyId);

    /**
     * 获取最大批次号
     */
    Integer getMaxBatchNumber(String endTime, int companyId);

    /**
     * 获取站的实时网络信息
     */
    List<Map<String, Object>> getStationRtNwData(Integer maxBatchNumber);

    //**********自定义结束*****////

}
