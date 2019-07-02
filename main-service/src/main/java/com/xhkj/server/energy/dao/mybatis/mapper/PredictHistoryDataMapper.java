package com.xhkj.server.energy.dao.mybatis.mapper;

import com.xhkj.server.energy.dao.mybatis.vo.PredictHistoryData;
import com.xhkj.server.energy.dao.mybatis.vo.PredictHistoryDataExample;
import com.xhkj.server.energy.page.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PredictHistoryDataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_para_cul
     *
     * @mbg.generated
     */
    long countByExample(PredictHistoryDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_para_cul
     *
     * @mbg.generated
     */
    int deleteByExample(PredictHistoryDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_para_cul
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Date opTime);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_para_cul
     *
     * @mbg.generated
     */
    int insert(PredictHistoryData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_para_cul
     *
     * @mbg.generated
     */
    int insertSelective(PredictHistoryData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_para_cul
     *
     * @mbg.generated
     */
    List<PredictHistoryData> selectByExample(PredictHistoryDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_para_cul
     *
     * @mbg.generated
     */
    PredictHistoryData selectByPrimaryKey(Date opTime);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_para_cul
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PredictHistoryData record, @Param("example") PredictHistoryDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_para_cul
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PredictHistoryData record, @Param("example") PredictHistoryDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_para_cul
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PredictHistoryData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_para_cul
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PredictHistoryData record);


    ////*******自定义开始********/
    List<PredictHistoryData> getPredictHistoryList(Page<PredictHistoryData> page);

    Integer updateByOpTimeSelective(PredictHistoryData predictHistoryData);

    PredictHistoryData getByOpTime(Date yesterdayDate);
    //***********自定义结束****////
}
