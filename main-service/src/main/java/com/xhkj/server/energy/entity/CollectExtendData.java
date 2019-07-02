package com.xhkj.server.energy.entity;

import com.xhkj.server.energy.dao.mybatis.vo.CollectData;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class CollectExtendData extends CollectData {

    private String standName;
    private String stationName;
    private String pt1_pt2;//一网压差
    private String pt3_pt4;//二网压差
    private String te1_te2;//一网水温差值
    private String te3_te4;//二网水温差值
    private String opTimeStr;//格式化后的日期

    private CollectExtendData() {
    }

    public CollectExtendData(CollectData collectData) {
        this.setOpTimeStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(collectData.getOpTime()));
        this.setPt1(collectData.getPt1());
        this.setPt2(collectData.getPt2());
        this.setPt1_pt2(round(collectData.getPt1() - collectData.getPt2()));//一网压差
        this.setTe1(collectData.getTe1());
        this.setTe2(collectData.getTe2());
        this.setTe1_te2(round(collectData.getTe1() - collectData.getTe2()));//一网温差
        this.setCvi1(collectData.getCvi1());
        this.setQi(collectData.getQi());
        this.setFt1(collectData.getFt1());
        this.setFt1q(collectData.getFt1q());
        this.setPt3(collectData.getPt3());
        this.setPt4(collectData.getPt4());
        this.setPt3_pt4(round(collectData.getPt3() - collectData.getPt4()));//二网压差
        this.setTe3(collectData.getTe3());
        this.setTe4(collectData.getTe4());
        this.setTe3_te4(round(collectData.getTe3() - collectData.getTe4()));
        this.setFt2(collectData.getFt2());
        this.setFt3(collectData.getFt3());
        this.setFc1v1(collectData.getFc1v1());
        this.setLt1(collectData.getLt1());
        this.setQqi(collectData.getQqi());
        this.setJqi(collectData.getJqi());
        this.setFt3q(collectData.getFt3q());
        this.setFt2q(collectData.getFt2q());
        this.setFt3qPrice(collectData.getFt3qPrice());
        this.setJqiPrice(collectData.getJqiPrice());
        this.setQqiPrice(collectData.getQqiPrice());
    }

    private String round(float v) {
        return new DecimalFormat("0.00").format(v);
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStandName() {
        return standName;
    }

    public void setStandName(String standName) {
        this.standName = standName;
    }

    public String getPt1_pt2() {
        return pt1_pt2;
    }

    public void setPt1_pt2(String pt1_pt2) {
        this.pt1_pt2 = pt1_pt2;
    }

    public String getPt3_pt4() {
        return pt3_pt4;
    }

    public void setPt3_pt4(String pt3_pt4) {
        this.pt3_pt4 = pt3_pt4;
    }

    public String getTe1_te2() {
        return te1_te2;
    }

    public void setTe1_te2(String te1_te2) {
        this.te1_te2 = te1_te2;
    }

    public String getTe3_te4() {
        return te3_te4;
    }

    public void setTe3_te4(String te3_te4) {
        this.te3_te4 = te3_te4;
    }

    public String getOpTimeStr() {
        return opTimeStr;
    }

    public void setOpTimeStr(String opTimeStr) {
        this.opTimeStr = opTimeStr;
    }
}
