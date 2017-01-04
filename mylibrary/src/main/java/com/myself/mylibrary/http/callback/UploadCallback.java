package com.myself.mylibrary.http.callback;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2016/5/11.
 */
public abstract class UploadCallback {
    public abstract void onSuccess(JSONObject result);
    public void onFail(){

    }

}
