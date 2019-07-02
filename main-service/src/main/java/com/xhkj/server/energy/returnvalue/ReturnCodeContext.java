package com.xhkj.server.energy.returnvalue;

import java.util.Map;

public interface ReturnCodeContext {

    void setAttribute(String key, Object value);

    Object getAttribute(String key);

    Map<String, Object> context();

}
