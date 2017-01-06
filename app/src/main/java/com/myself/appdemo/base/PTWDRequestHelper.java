package com.myself.appdemo.base;

import com.myself.appdemo.TotalApplication;
import com.myself.appdemo.db.AccountHelper;
import com.myself.mylibrary.http.request.FormEncodingRequestBuilder;
import com.myself.mylibrary.util.AppUtils;

/**
 * 继承固定请求参数
 * Created by guchenkai on 2015/11/26.
 */
public class PTWDRequestHelper {
    //===================request key================================
    public static final String REQUEST_KEY_UID = "uid";
    public static final String REQUEST_KEY_TOKEN = "token";
    public static final String REQUEST_KEY_DEVICE_ID = "device_id";
    public static final String REQUEST_KEY_APP_ID = "appid";

    public static final String REQUEST_KEY_START_DEVICE_NAME = "device_name";


    /**
     * 封装固定请求参数(葡商城使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder store() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, TotalApplication.app_id)
                .addParam(PTWDRequestHelper.REQUEST_KEY_DEVICE_ID, TotalApplication.app_device_id);
    }

    /**
     * 封装固定请求参数(葡商城使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder start() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
                .addParam(PTWDRequestHelper.REQUEST_KEY_DEVICE_ID, TotalApplication.app_device_id)
                .addParam(PTWDRequestHelper.REQUEST_KEY_START_DEVICE_NAME, AppUtils.getDeviceName())
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, TotalApplication.app_id);
    }

    /**
     * 封装固定请求参数(探索号使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder explore() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
                .addParam(PTWDRequestHelper.REQUEST_KEY_DEVICE_ID, TotalApplication.app_device_id)
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, TotalApplication.app_id);
    }

    /**
     * 封装固定请求参数(用户使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder user() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
                .addParam(PTWDRequestHelper.REQUEST_KEY_DEVICE_ID, TotalApplication.app_device_id)
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, TotalApplication.app_id);
    }

    /**
     * 封装固定请求参数(发现视频使用)
     */
    public static FormEncodingRequestBuilder find() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, TotalApplication.app_id)
                .addParam(PTWDRequestHelper.REQUEST_KEY_DEVICE_ID, TotalApplication.app_device_id);

    }

    /**
     * 封装固定请求参数(购物车使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder shopCar() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
                .addParam(PTWDRequestHelper.REQUEST_KEY_APP_ID, TotalApplication.app_id)
                .addParam(PTWDRequestHelper.REQUEST_KEY_DEVICE_ID, TotalApplication.app_device_id);
    }

    /**
     * 封装固定请求参数(产品查看详情使用)
     */
    public static FormEncodingRequestBuilder details() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken())
                .addParam(PTWDRequestHelper.REQUEST_KEY_DEVICE_ID, TotalApplication.app_device_id);
    }

    /**
     * 封装固定请求参数(上传使用)
     *
     * @return Request实例
     */
    public static FormEncodingRequestBuilder upload() {
        return FormEncodingRequestBuilder.newInstance()
                .addParam(REQUEST_KEY_UID, AccountHelper.getCurrentUid())
                .addParam(PTWDRequestHelper.REQUEST_KEY_DEVICE_ID, TotalApplication.app_device_id);
    }
}
