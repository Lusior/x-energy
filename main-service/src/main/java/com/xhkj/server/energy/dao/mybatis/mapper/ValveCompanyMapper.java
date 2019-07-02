package com.xhkj.server.energy.dao.mybatis.mapper;

import com.xhkj.server.energy.dao.mybatis.vo.ValveCompany;
import com.xhkj.server.energy.dao.mybatis.vo.ValveCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ValveCompanyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_valve_company
     *
     * @mbg.generated
     */
    long countByExample(ValveCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_valve_company
     *
     * @mbg.generated
     */
    int deleteByExample(ValveCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_valve_company
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_valve_company
     *
     * @mbg.generated
     */
    int insert(ValveCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_valve_company
     *
     * @mbg.generated
     */
    int insertSelective(ValveCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_valve_company
     *
     * @mbg.generated
     */
    List<ValveCompany> selectByExample(ValveCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_valve_company
     *
     * @mbg.generated
     */
    ValveCompany selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_valve_company
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ValveCompany record, @Param("example") ValveCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_valve_company
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ValveCompany record, @Param("example") ValveCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_valve_company
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ValveCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_valve_company
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ValveCompany record);


    ////*******自定义开始********/
    //***********自定义结束****////
}
