/**
 * Project Name:szpay
 * File Name:MenuPage.java
 * Package Name:com.sz.core.sys.entity
 * Date:2015年9月9日上午10:55:08
 *
 */

package com.xhkj.server.energy.entity;

/**
 * ClassName:MenuPage <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年9月9日 上午10:55:08 <br/>
 * 
 * @author fuwei
 * @version V1.0
 * @see
 */
public class MenuPage {

	private String id;
	private boolean isexpand;
	private String code;
	private String pid;
	private String url;
	private String text;
	private String order;
	private String type;
	private boolean ischecked;
	private String icon;
	private boolean indeterminate = false;
	/** 如果为叶节点 值为1 */
	private String isLeaf;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isIschecked() {
		return ischecked;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isIsexpand() {
		return isexpand;
	}

	public void setIsexpand(boolean isexpand) {
		this.isexpand = isexpand;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public boolean isIndeterminate() {
		return indeterminate;
	}

	public void setIndeterminate(boolean indeterminate) {
		this.indeterminate = indeterminate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MenuPage [id=");
		builder.append(id);
		builder.append(", isexpand=");
		builder.append(isexpand);
		builder.append(", code=");
		builder.append(code);
		builder.append(", pid=");
		builder.append(pid);
		builder.append(", url=");
		builder.append(url);
		builder.append(", text=");
		builder.append(text);
		builder.append(", order=");
		builder.append(order);
		builder.append(", type=");
		builder.append(type);
		builder.append(", ischecked=");
		builder.append(ischecked);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", indeterminate=");
		builder.append(indeterminate);
		builder.append(", isLeaf=");
		builder.append(isLeaf);
		builder.append("]");
		return builder.toString();
	}

}
