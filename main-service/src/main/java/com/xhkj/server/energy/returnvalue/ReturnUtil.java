package com.xhkj.server.energy.returnvalue;

public class ReturnUtil {

    public static boolean eq(ReturnCode returnCode1, ReturnCode returnCode2) {
        return returnCode1.getCode() == returnCode2.getCode();
    }

    public static <T> ReturnValue<T> creatSuccessful(T value) {
        return new ReturnValue<T>(value);
    }

    public static ReturnValue<?> creatSuccessful() {
        return creatSuccessful(VoidValue.instance);
    }

}
