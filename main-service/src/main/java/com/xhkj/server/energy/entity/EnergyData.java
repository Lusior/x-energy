package com.xhkj.server.energy.entity;

public class EnergyData extends BaseEntity {
    /**
     * app总览那里，按换热站分组的三耗
     */
    private static final long serialVersionUID = 1L;
    private String stationId;
    private String stationName;//换热站名称
    private String standId;
    private String standName;
    private String ft3q;
    private String jqi;
    private String qqi;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStandId() {
        return standId;
    }

    public void setStandId(String standId) {
        this.standId = standId;
    }

    public String getStandName() {
        return standName;
    }

    public void setStandName(String standName) {
        this.standName = standName;
    }

    public String getFt3q() {
        return ft3q;
    }

    public void setFt3q(String ft3q) {
        this.ft3q = ft3q;
    }

    public String getJqi() {
        return jqi;
    }

    public void setJqi(String jqi) {
        this.jqi = jqi;
    }

    public String getQqi() {
        return qqi;
    }

    public void setQqi(String qqi) {
        this.qqi = qqi;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StationData [stationId=");
        builder.append(stationId);
        builder.append(", stationName=");
        builder.append(stationName);
        builder.append(", ft3q=");
        builder.append(ft3q);
        builder.append(", jqi=");
        builder.append(jqi);
        builder.append(", qqi=");
        builder.append(qqi);
        builder.append("]");
        return builder.toString();
    }
}
