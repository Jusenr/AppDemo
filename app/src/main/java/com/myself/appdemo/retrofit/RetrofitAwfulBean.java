package com.myself.appdemo.retrofit;

import java.io.Serializable;

/**
 * Created by riven_chris on 16/7/14.
 */
public class RetrofitAwfulBean implements Serializable {
    protected int error_code;
    protected String msg;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
