package com.xhkj.server.energy.dao.mybatis.mapper;

import com.xhkj.server.energy.dao.mybatis.vo.CollectDataDay;
import com.xhkj.server.energy.dao.mybatis.vo.CollectDataDayExample;
import com.xhkj.server.energy.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CollectDataDayMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_collect_day
     *
     * @mbg.generated
     */
    long countByExample(CollectDataDayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_collect_day
     *
     * @mbg.generated
     */
    int deleteByExample(CollectDataDayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_collect_day
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_collect_day
     *
     * @mbg.generated
     */
    int insert(CollectDataDay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_collect_day
     *
     * @mbg.generated
     */
    int insertSelective(CollectDataDay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_collect_day
     *
     * @mbg.generated
     */
    List<CollectDataDay> selectByExample(CollectDataDayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_collect_day
     *
     * @mbg.generated
     */
    CollectDataDay selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_collect_day
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") CollectDataDay record, @Param("example") CollectDataDayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_collect_day
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") CollectDataDay record, @Param("example") CollectDataDayExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_collect_day
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(CollectDataDay record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_collect_day
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CollectDataDay record);


    ////*******自定义开始********/

    List<Map<String, Object>> getAnalysisWater(Page page);

    List<Map<String, Object>> getAnalysisElectricity(Page page);

    List<Map<String, Object>> getAnalysisHeat(Page page);

    float getTotalHeat(Date yesterdayDate);

    List<Map<String, Object>> getDataEconByDateTime(Page page);

    //***********自定义结束****////
}
