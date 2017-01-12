package com.myself.appdemo.qrcode.model.bean;

import com.myself.appdemo.retrofit.RetrofitAwfulBean;

/**
 * Created by zsw on 2016/12/19.
 */

public class CheckBindResult extends RetrofitAwfulBean {
    private String is_bind;

    public String getIs_bind() {
        return is_bind;
    }

    public void setIs_bind(String is_bind) {
        this.is_bind = is_bind;
    }
}
