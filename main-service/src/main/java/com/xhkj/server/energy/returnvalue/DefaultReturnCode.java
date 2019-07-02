package com.xhkj.server.energy.returnvalue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DefaultReturnCode implements ReturnCode, ReturnCodeContext, Serializable {

    private static final long serialVersionUID = 4271872452641245930L;

    protected int code;
    protected String desc;
    protected Map<String, Object> context;

    public DefaultReturnCode() {
        this.code = ReturnCode.SUCCESS;
        this.desc = "SUCCESS";
    }

    public DefaultReturnCode(ReturnCode returnCode) {
        this(returnCode.getCode(), returnCode.getDesc());
        if (returnCode instanceof ReturnCodeContext) {
            this.context = ((ReturnCodeContext) returnCode).context();
        }
    }

    public DefaultReturnCode(int code, String desc) {
        super();
        this.code = code;
        this.desc = desc;
        context = new HashMap<>();
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setAttribute(String key, Object value) {
        context.put(key, value);
    }

    @Override
    public Object getAttribute(String key) {
        return context.get(key);
    }

    public Map<String, Object> context() {
        return context;
    }

}
