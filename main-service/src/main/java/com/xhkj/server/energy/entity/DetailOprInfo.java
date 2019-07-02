package com.xhkj.server.energy.entity;

import com.xhkj.server.energy.dao.mybatis.vo.OprInfo;

public class DetailOprInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private OprInfo oprInfo;
	
	private int logonID;

	public OprInfo getOprInfo() {
		return oprInfo;
	}

	public void setOprInfo(OprInfo oprInfo) {
		this.oprInfo = oprInfo;
	}

	public int getLogonID() {
		return logonID;
	}

	public void setLogonID(int logonID) {
		this.logonID = logonID;
	}
}
