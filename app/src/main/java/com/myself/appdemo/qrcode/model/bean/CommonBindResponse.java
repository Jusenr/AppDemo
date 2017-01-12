package com.myself.appdemo.qrcode.model.bean;

import com.myself.appdemo.retrofit.RetrofitAwfulBean;

import java.io.Serializable;

/**
 * 绑定子账号
 * Created by zsw on 2016/7/7.
 */
public class CommonBindResponse extends RetrofitAwfulBean implements Serializable {
    private String code;
    private String error;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
