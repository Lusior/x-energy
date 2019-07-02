package com.xhkj.server.energy.exception;

import com.xhkj.server.energy.returnvalue.ReturnValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(MyException.class)
    public ReturnValue xSdkException(MyException e) {
        return new ReturnValue(e);
    }

    @ExceptionHandler(Exception.class)
    public ReturnValue uncaughtException(Exception e) {
        log.error(e.getMessage(), e);
        return new ReturnValue(MyExceptionEnum.SYSTEM_EXCEPTION);
    }
}
