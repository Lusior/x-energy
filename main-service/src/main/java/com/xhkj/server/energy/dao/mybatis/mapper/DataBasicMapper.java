package com.xhkj.server.energy.dao.mybatis.mapper;

import com.xhkj.server.energy.dao.mybatis.vo.DataBasic;
import com.xhkj.server.energy.dao.mybatis.vo.DataBasicExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataBasicMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_basic
     *
     * @mbg.generated
     */
    long countByExample(DataBasicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_basic
     *
     * @mbg.generated
     */
    int deleteByExample(DataBasicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_basic
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_basic
     *
     * @mbg.generated
     */
    int insert(DataBasic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_basic
     *
     * @mbg.generated
     */
    int insertSelective(DataBasic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_basic
     *
     * @mbg.generated
     */
    List<DataBasic> selectByExample(DataBasicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_basic
     *
     * @mbg.generated
     */
    DataBasic selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_basic
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") DataBasic record, @Param("example") DataBasicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_basic
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") DataBasic record, @Param("example") DataBasicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_basic
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DataBasic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_basic
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DataBasic record);


    ////*******自定义开始********/
    //***********自定义结束****////
}
