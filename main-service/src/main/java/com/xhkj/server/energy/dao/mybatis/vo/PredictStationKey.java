package com.xhkj.server.energy.dao.mybatis.vo;

import java.util.Date;

public class PredictStationKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_predict_station.OP_TIME
     *
     * @mbg.generated
     */
    private Date opTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_predict_station.COMPANY_ID
     *
     * @mbg.generated
     */
    private Integer companyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column data_predict_station.STATION_ID
     *
     * @mbg.generated
     */
    private String stationId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column data_predict_station.OP_TIME
     *
     * @return the value of data_predict_station.OP_TIME
     *
     * @mbg.generated
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column data_predict_station.OP_TIME
     *
     * @param opTime the value for data_predict_station.OP_TIME
     *
     * @mbg.generated
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column data_predict_station.COMPANY_ID
     *
     * @return the value of data_predict_station.COMPANY_ID
     *
     * @mbg.generated
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column data_predict_station.COMPANY_ID
     *
     * @param companyId the value for data_predict_station.COMPANY_ID
     *
     * @mbg.generated
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column data_predict_station.STATION_ID
     *
     * @return the value of data_predict_station.STATION_ID
     *
     * @mbg.generated
     */
    public String getStationId() {
        return stationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column data_predict_station.STATION_ID
     *
     * @param stationId the value for data_predict_station.STATION_ID
     *
     * @mbg.generated
     */
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }


    ////*******自定义开始********/
    //***********自定义结束****////
}
