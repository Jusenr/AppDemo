package com.myself.appdemo;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2017/1/4 17:19.
 */

public class Constants {

    public static class EventKey {
        public static final String EVENT_CLICK_ITEM = "event_click_item";

    }

    public static class BundleKey {
        public static final String BUNDLE_APP_INFO = "app_info";
        public static final String BUNDLE_ACTIVATION = "bundle_activation";//激活
        public static final String BUNDLE_GOODS_NAME = "bundle_goods_name";//产品名称
        public static final String BUNDLE_ASSIST_IN_LOGGING_IN = "bundle_assist_in_logging_in";//协助登录

    }

    public static class ParamKey {
        public static final String PARAM_CID = "cid";
        public static final String PARAM_GOODS_ID = "goods_id";//---------------------产品PID
        public static final String PARAM_GOODS_APPID = "goods_appid";//---------------产品对应appid

    }

    public static class SPKey {
        public static final String PREFERENCE_KEY_DEVICE_ID = "key_device_id";

    }

    public static class TypeKey {
        public static final String TYPE_ACTIVE = "active";//--------------------------已激活
        public static final String TYPE_UNACTIVATED = "unactivated";//----------------未激活
        public static final String TYPE_CREATE_CHILD = "type_create_child";//----------------扫描产品二维码，区分创建孩子和为已有孩子激活产品逻辑
        public static final String TYPE_HELP_LOGIN = "type_help_login";//----------------扫描已有产品二维码去协助登录
        public static final String TYPE_MEDIA_TYPE_H5 = "h5";
        public static final String TYPE_MEDIA_TYPE_IMAGE = "image";

    }

    public static class CacheKey {
        public static String CACHE_DFU_ZIP_REFERENCE_ID = "cache_dfu_zip_reference_id";

    }

    public static class ReceiverAction {
        public static String PAIBAND_SAFE_DISTANCE = "paiband_safe_distance";

    }
}
