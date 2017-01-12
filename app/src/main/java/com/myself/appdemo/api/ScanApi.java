package com.myself.appdemo.api;

import com.myself.mylibrary.http.request.FormEncodingRequestBuilder;
import com.myself.mylibrary.http.request.RequestMethod;

import okhttp3.Request;

/**
 * 扫描Api
 * Created by guchenkai on 2015/12/23.
 */
public class ScanApi {

    /**
     * 扫描登录官网
     *
     * @param url url
     */
    public static Request scanLogin(String url) {
        return FormEncodingRequestBuilder.newInstance()
                .build(RequestMethod.GET, url);
    }

    /**
     * 确认登录
     *
     * @param url url
     */
    public static Request confirmLogin(String url) {
        StringBuffer sb = new StringBuffer(url);
        sb.append("&ok=").append("1");
        url = sb.toString();
        return FormEncodingRequestBuilder.newInstance()
                .build(RequestMethod.GET, url);
    }
}
