package com.xhkj.server.energy.exception;

import com.xhkj.server.energy.returnvalue.ReturnCode;

public enum MyExceptionEnum implements ReturnCode {

    ACCOUNT_NOT_EXIST(1, "该账号不存在！"),
    NO_LOGIN(2, "没有登录，请先登录"),
    PARAMETER_ERROR(3, "参数错误"),
    OPERATION_TOO_FREQUENT(4, "操作频繁"),
    PLEASE_TRY_LATER(5, "请稍后再试！"),
    HTTP_CONNECT_FAIELD(6, "HTTP连接失败！"),
    HTTP_READ_FAIELD(7, "HTTP READ 失败！"),
    SYSTEM_EXCEPTION(500, "系统异常");

    private int code;
    private String desc;

    MyExceptionEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
