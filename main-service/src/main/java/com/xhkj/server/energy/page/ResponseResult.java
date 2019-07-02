package com.xhkj.server.energy.page;

/**
 * ClassName:ResponseResult <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 在新增，修改等ajax请求时，返回的结果封装
 * Date:     2015年9月10日 下午6:31:44 <br/>
 *
 * @author benq
 * @version V1.0
 * @see
 */
public class ResponseResult {
    private static final long serialVersionUID = -3794849863472722856L;

    private boolean success;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

