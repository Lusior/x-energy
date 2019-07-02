package com.xhkj.server.energy.exception;

import com.xhkj.server.energy.returnvalue.ReturnCode;

public class MyException extends RuntimeException implements ReturnCode {

    private ReturnCode returnCode;

    public MyException(Throwable cause) {
        super(cause);
    }

    public MyException(ReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    @Override
    public int getCode() {
        return returnCode.getCode();
    }

    @Override
    public String getDesc() {
        return returnCode.getDesc();
    }
}
