package com.xhkj.server.energy.entity;

import com.xhkj.server.energy.dao.mybatis.vo.CollectDataDay;

public class ReportDto {

    private String stationId;
    private String station_name;
    private float day_qqi_total;
    private float day_qqi;
    private float day_jqi_total;
    private float day_jqi;
    private float day_ft3q_total;
    private float day_ft3q;

    public ReportDto(CollectDataDay dataDay) {
        this.stationId = dataDay.getStationId();
        this.station_name = dataDay.getStationId();
        this.day_qqi_total = dataDay.getQqiTotal();
        this.day_qqi = dataDay.getQqi();
        this.day_jqi_total = dataDay.getJqiTotal();
        this.day_jqi = dataDay.getJqi();
        this.day_ft3q_total = dataDay.getFt3qTotal();
        this.day_ft3q = dataDay.getFt3q();
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public float getDay_qqi_total() {
        return day_qqi_total;
    }

    public void setDay_qqi_total(float day_qqi_total) {
        this.day_qqi_total = day_qqi_total;
    }

    public float getDay_qqi() {
        return day_qqi;
    }

    public void setDay_qqi(float day_qqi) {
        this.day_qqi = day_qqi;
    }

    public float getDay_jqi_total() {
        return day_jqi_total;
    }

    public void setDay_jqi_total(float day_jqi_total) {
        this.day_jqi_total = day_jqi_total;
    }

    public float getDay_jqi() {
        return day_jqi;
    }

    public void setDay_jqi(float day_jqi) {
        this.day_jqi = day_jqi;
    }

    public float getDay_ft3q_total() {
        return day_ft3q_total;
    }

    public void setDay_ft3q_total(float day_ft3q_total) {
        this.day_ft3q_total = day_ft3q_total;
    }

    public float getDay_ft3q() {
        return day_ft3q;
    }

    public void setDay_ft3q(float day_ft3q) {
        this.day_ft3q = day_ft3q;
    }
}
