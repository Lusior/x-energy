package com.xhkj.server.energy.returnvalue;

import java.io.Serializable;

public class ReturnValue<T> extends DefaultReturnCode implements Serializable {

	private static final long serialVersionUID = 483985436825322483L;

	private T value;

	public ReturnValue() {
		super();
	}

	public ReturnValue(T value) {
		super();
		this.value = value;
	}

	public ReturnValue(ReturnCode returnCode) {
		super(returnCode);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean success() {
		return ReturnCode.SUCCESS == code;
	}

}
