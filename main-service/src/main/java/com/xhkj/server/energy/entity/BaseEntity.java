package com.xhkj.server.energy.entity;

import com.xhkj.server.energy.utils.DateUtils;

import java.io.Serializable;

/**
 * @author fuwei
 * @ClassName: BaseEntity
 * @Description: TODO(entity的抽象类)
 * @date 2015年8月28日 上午10:53:15
 */
public abstract class BaseEntity implements Serializable {
    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 1L;

    protected String crtId;
    protected String crtNm;
    protected String crtDt;
    protected String crtTm;
    protected String uptId;
    protected String uptNm;
    protected String uptDt;
    protected String uptTm;

    // 时间戳
    protected String tmSmp;


    public String getCrtId() {
        return crtId;
    }

    public void setCrtId(String crtId) {
        this.crtId = crtId;
    }

    public void setUptId(String uptId) {
        this.uptId = uptId;
    }

    public String getCrtDt() {
        return crtDt;
    }

    public void setCrtDt(String crtDt) {
        this.crtDt = crtDt;
    }

    public String getCrtTm() {
        return crtTm;
    }

    public void setCrtTm(String crtTm) {
        this.crtTm = crtTm;
    }

    public String getUptDt() {
        return uptDt;
    }

    public void setUptDt(String uptDt) {
        this.uptDt = uptDt;
    }

    public String getUptTm() {
        return uptTm;
    }

    public void setUptTm(String uptTm) {
        this.uptTm = uptTm;
    }

    /**
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getCrtDateTime
     * @Description: TODO(返回创建日期与创建时间的合并)
     */
    public String getCrtDtTm() {
        return DateUtils.toString(DateUtils.toDate(crtDt + crtTm), "yyyyMMdd HH:mm:ss");

    }

    /**
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getUptDateTime
     * @Description: TODO(返回更新日期与更新时间的合并)
     */
    public String getUptDateTime() {
        return this.uptDt + this.uptTm;
    }

    public String getCrtNm() {
        return crtNm;
    }

    public void setCrtNm(String crtNm) {
        this.crtNm = crtNm;
    }

    public String getUptNm() {
        return uptNm;
    }

    public void setUptNm(String uptNm) {
        this.uptNm = uptNm;
    }

    public String getTmSmp() {
        return tmSmp;
    }

    public void setTmSmp(String tmSmp) {
        this.tmSmp = tmSmp;
    }
}