package com.myself.appdemo.retrofit.api;

/**
 * Created by xiaopeng on 16/7/11.
 * <p>
 * create at 16/7/11 下午1:51
 */

public class BaseApi {

    public static final int HOST_FORMAL = 1;//正式环境
    public static final int HOST_DEV = 2;//开发环境
    public static final int HOST_TEST = 3;//测试环境

    public static int HOST_NOW;//当前环境
    /*所有的BASE URL*/
    public static String WEIDU_BASE_URL = "";

    /**
     * 二维码服务器
     */
    public static String QRCODE_BASE_URL = "";

    /**
     * 蓝牙
     */
    public static String PAIBAND_DEVICE_BASE_URL = "";

    /**
     * environment: 1，外网 2，开发内网，3，测试内网
     */
    public static void init(int environment) {
        HOST_NOW = environment;
        switch (environment) {
            case 1:
                WEIDU_BASE_URL = "http://api-weidu.putao.com/";
                QRCODE_BASE_URL = "http://qrcode-ws.putao.com/";
                PAIBAND_DEVICE_BASE_URL = "http://device-service.putao.com/";
                break;
            case 2:
                WEIDU_BASE_URL = "http://dev-api-weidu.ptdev.cn/";
                QRCODE_BASE_URL = "http://dev-qrcode-ws.ptdev.cn/";
                PAIBAND_DEVICE_BASE_URL = "http://dev-device-info-server.ptdev.cn/";
                break;
            case 3:
                WEIDU_BASE_URL = "http://test-api-weidu.ptdev.cn/";
                QRCODE_BASE_URL = "http://test-qrcode-ws.ptdev.cn/";
                PAIBAND_DEVICE_BASE_URL = "http://test-device-info-server.ptdev.cn/";
                break;
        }
    }

    public static boolean isDebug() {
        return HOST_NOW == HOST_DEV || HOST_NOW == HOST_TEST;
    }

    /*所有的相对URL*/
    public static class Url {

        public static final String URL_CHILD_REFRESH = "test";
        public static final String URL_USER_COLLECTS = "test";
        public static final String URL_USER_ADVICE = "test";
        public static final String URL_ARTICLE_INDEX = "test";

        /**
         * 扫码关注产品（添加）
         */
        public static final String URL_SCAN_ADD = WEIDU_BASE_URL + "scan/add";

        public static final String url_scan_add_deivce = WEIDU_BASE_URL + "get/captcha";

        /**
         * 设备管理(需登录)
         */
        public static final String URL_ADD_SCAN = WEIDU_BASE_URL + "scan/add";

        /**
         * PAIBOT扫码流程
         */
        public static final String CHILD_GET_LOGIN_URL = WEIDU_BASE_URL + "child/get/loginurl";

        /**
         * 二维码扫码流程
         */
        public static final String QR_SERVER_HANDLER = QRCODE_BASE_URL + "handler";

        /**
         * PAIBOT扫码流程
         */
        public static final String URL_CHILD_GET_LOGIN_URL = "child/get/loginurl";
        /**
         * 二维码扫码流程
         */
        public static final String URL_QR_SERVER_HANDLER = "handler";

    }

}
