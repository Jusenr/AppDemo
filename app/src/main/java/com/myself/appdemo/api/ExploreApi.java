package com.myself.appdemo.api;

import com.myself.appdemo.base.PTWDRequestHelper;
import com.myself.appdemo.retrofit.api.BaseApi;
import com.myself.mylibrary.http.request.RequestMethod;
import com.myself.mylibrary.util.AppUtils;

import okhttp3.Request;

/**
 * 探索号接口
 * Created by guchenkai on 2015/12/8.
 */
public class ExploreApi {
    private static final String REQUEST_SLAVE_DEVICE_ID = "slave_device_id";//关联设备唯一id，无关注设备时不请求
    private static final String SLAVE_ID = "slave_id";//设备id
    private static final String REQUEST_START_TIME = "start_time";//起始时间的时间戳
    private static final String REQUEST_END_TIME = "end_time";//结束时间的时间戳
    private static final String REQUEST_PRODUCT_ID = "product_id";//产品id
    private static final String REQUEST_EDIT_LIST = "edit_list";//用户修改的数据列表
    private static final String REQUEST_PLOT_ID = "plot_id";//剧情理念详情id
    private static final String REQUEST_PAGE = "page";//页码

    private static final String LIMIT = "limit";//首页数据条目数量
    private static final String ARTICLE_ID = "article_id";//首页文章ID
    private static final String WD_MID = "wd_mid";//评论页文章ID
    private static final String CONTENT = "content";//文章评论内容
    private static final String COMMENT_ID = "comment_id";//首页评论ID
    private static final String MESSAGE = "message";//首页评论内容
    private static final String COOL_TYPE = "cool_type";//赞类型
    private static final String IS_DISPLAY_EXPLANATION = "is_display_explanation";//是否请求文章内容
    private static final String SERVICE_ID = "sid"; //服务号唯一service_id
    private static final String PICTURES = "pics";//评论图片


    private static final String CAPTCHA_TOKEN = "captcha_token";//扫描受控设备二维码获取的参数
    private static final String REQUEST_MASTER_DEVICE_NAME = "master_device_name";//控制设备名称
    private static final String STATUS = "status";//控制设备名称


    public static void install(String base_url) {
//        WEIDU_BASE_URL = base_url;
    }

    /**
     * 扫码关注产品（添加）
     *
     * @param slave_device_id 受控设备id号
     * @param product_id      对应产品idc
     */
    public static Request scanAdd(String slave_device_id, String product_id) {
        return PTWDRequestHelper.explore()
                .addParam(REQUEST_SLAVE_DEVICE_ID, slave_device_id)
                .addParam(REQUEST_PRODUCT_ID, product_id)
                .build(RequestMethod.POST, BaseApi.Url.URL_SCAN_ADD);
    }

    /**
     * 扫码关注产品（维度客户端添加）
     *
     * @param captcha_token 受控设备id号
     */
    public static Request addDevice(String captcha_token) {
        return PTWDRequestHelper.explore()
                .addParam(CAPTCHA_TOKEN, captcha_token)
                .addParam(REQUEST_MASTER_DEVICE_NAME, AppUtils.getDeviceName())
                .build(RequestMethod.POST, BaseApi.Url.URL_SCAN_ADD);
    }
}
