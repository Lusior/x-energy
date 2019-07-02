package com.xhkj.server.energy.dao.mybatis.mapper;

import com.xhkj.server.energy.dao.mybatis.vo.StationData;
import com.xhkj.server.energy.dao.mybatis.vo.StationDataExample;
import com.xhkj.server.energy.dao.mybatis.vo.StationDataKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StationDataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_station
     *
     * @mbg.generated
     */
    long countByExample(StationDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_station
     *
     * @mbg.generated
     */
    int deleteByExample(StationDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_station
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(StationDataKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_station
     *
     * @mbg.generated
     */
    int insert(StationData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_station
     *
     * @mbg.generated
     */
    int insertSelective(StationData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_station
     *
     * @mbg.generated
     */
    List<StationData> selectByExample(StationDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_station
     *
     * @mbg.generated
     */
    StationData selectByPrimaryKey(StationDataKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_station
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") StationData record, @Param("example") StationDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_station
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") StationData record, @Param("example") StationDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_station
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(StationData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_station
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(StationData record);


    ////*******自定义开始********/
    //***********自定义结束****////
}
