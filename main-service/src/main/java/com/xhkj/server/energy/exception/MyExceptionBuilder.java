package com.xhkj.server.energy.exception;

import com.xhkj.server.energy.returnvalue.ReturnCode;

public class MyExceptionBuilder {

    private MyException myException;

    private MyExceptionBuilder(ReturnCode returnCode) {
        this.myException = new MyException(returnCode);
    }

    public static MyExceptionBuilder newBuilder(ReturnCode returnCode) {
        return new MyExceptionBuilder(returnCode);
    }

    public MyException build() {
        return myException;
    }
}
