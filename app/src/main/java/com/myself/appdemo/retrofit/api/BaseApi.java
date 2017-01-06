package com.myself.appdemo.retrofit.api;

/**
 * Created by xiaopeng on 16/7/11.
 *
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
                PAIBAND_DEVICE_BASE_URL = "http://device-service.putao.com/";
                break;
            case 2:
                WEIDU_BASE_URL = "http://dev-api-weidu.ptdev.cn/";
                PAIBAND_DEVICE_BASE_URL = "http://dev-device-info-server.ptdev.cn/";
                break;
            case 3:
                WEIDU_BASE_URL = "http://test-api-weidu.ptdev.cn/";
                PAIBAND_DEVICE_BASE_URL = "http://test-device-info-server.ptdev.cn/";
                break;
        }
    }

    public static boolean isDebug() {
        return HOST_NOW == HOST_DEV || HOST_NOW == HOST_TEST;
    }

    /*所有的相对URL*/
    public static class Url {
        //=====================AccountApi ========================//
        //================CollectionApi==================//
        public static final String URL_USER_COLLECTS = WEIDU_BASE_URL + "user/collects";
        //================AdviceApi==================//
        public static final String URL_USER_ADVICE = WEIDU_BASE_URL + "submit/feedback";

        //================CompanionApi==================//
        /**
         * 设备管理(需登录)
         */
        public static final String URL_ADD_SCAN = WEIDU_BASE_URL + "scan/add";
        /**
         * 管理产品（查询）
         */
        public static final String URL_MANAGEMENT_INDEX = WEIDU_BASE_URL + "management/index";
        /**
         * 陪伴首页
         */
        public static final String URL_SERVICE_RELATION = WEIDU_BASE_URL + "service/user/relation";
        /**
         * 菜单列表
         */
        public static final String URL_COMPANY_SERVICE_MENUS = WEIDU_BASE_URL + "company/service/menus";
        /**
         * 公众号首页消息获取
         */
        public static final String URL_SERVICE_MESSAGE_LISTS = WEIDU_BASE_URL + "service/message/lists";
        /**
         * 公众号消息更新接口
         */
        public static final String URL_SERVICE_MESSAGE_UPDATED = WEIDU_BASE_URL + "updated/push/message";
        /**
         * 点赞
         */
        public static final String URL_COMPANY_ARTICLE_LIKE = WEIDU_BASE_URL + "company/article/like";
        /**
         * 公众号首页消息获取
         */
        public static final String URL_SERVICE_INFO = WEIDU_BASE_URL + "service/info";
        /**
         * 获取商城消息模板
         */
        public static final String URL_SERVICE_GET_TEMPLATE_ID = WEIDU_BASE_URL + "notice/get/template";
        /**
         * 获取管控号模版接口（客户端调用）
         */
        public static final String URL_CHILD_GET_TEMPLATE_ID = WEIDU_BASE_URL + "child/get/template";
        /**
         * 孩子能力记录
         */
        public static final String URL_CHILD_GET_ABILITY_LOG = "child/ability/log";
        /**
         * 孩子能力列表
         */
        public static final String URL_CHILD_GET_ABILITY_LIST = "child/ability";
        /**
         * 孩子首页
         */
        public static final String URL_CHILD_INDEX = "child/index";
        /**
         * 孩子列表
         */
        public static final String URL_CHILD_LIST = "child/list";
        /**
         * 刷新用户关联的孩子数据（添加、删除孩子时）
         */
        public static final String URL_CHILD_REFRESH = "child/refresh";
        /**
         * 获取如何关联介绍
         */
        public static final String URL_GET_BIND_INTRODUCE = "get/bindIntroduce";
        /**
         * 摇一摇获取随机一首儿童歌曲
         */
        public static final String URL_SHAKE_MUSIC = "shake/music";
        /**
         * 摇一摇
         */
        public static final String URL_SHAKE = "shake";
        /**
         * 协助登录通知所有家长
         */
        public static final String URL_ASSIST_LOGIN_NOTICE = "assist/login/notice";
        /**
         * 关注服务号
         */
        public static final String URL_SERVICE_RELATION_ACTION = "service/relation";
        /**
         * 个人简介
         */
        public static final String URL_USER_EDIT_ACTION = "user/edit";
        /**
         * 强制更新
         */
        public static final String URL_GET_VERSION = "get/version";
        /**
         * 产品列表
         */
        public static final String URL_GOODS_LIST = "goods/list";
        /**
         * 产品首页
         */
        public static final String URL_GOODS_INDEX = "goods/index";
        /**
         * 解除绑定
         */
        public static final String URL_SERVICE_BIND_DEL = "service/bindDel";

        /**
         * 文章评论
         */
        public static final String URL_COMPANY_ARTICLE_COMMENTS = WEIDU_BASE_URL + "company/article/comments";
        /**
         * 文章详情评论
         */
        public static final String URL_COMPANY_ARTICLE_COMMENT_URL = WEIDU_BASE_URL + "second/comment";
        /**
         * 发布评论
         */
        public static final String URL_COMPANY_ARTICLE_ADDCOMMENT = WEIDU_BASE_URL + "company/article/addcomment";
        /**
         * 二级回复评论 参数
         */
        public static final String URL_COMMENT_ADD = WEIDU_BASE_URL + "set/second/comment";
        /**
         * 二级回复评论点赞 参数
         */
        public static final String URL_COMMENT_PARISE_ADD = WEIDU_BASE_URL + "set/second/like";
        /**
         * 查看历史文章信息
         */
        public static final String URL_COMMPAIN_MESSAGE_PUBLIC = WEIDU_BASE_URL + "service/message/public";
        /**
         * v1.3
         * 添加收藏
         */
        public static final String URL_USER_COLLECTS_ADD = WEIDU_BASE_URL + "user/collects/add";
        /**
         * 取消收藏
         */
        public static final String URL_COMPANY_CANCEL_COLLECTION = WEIDU_BASE_URL + "user/collects/del";
        /**
         * v1.3
         * 菜单列表
         */
        public static final String URL_SERVICE_MENNU = WEIDU_BASE_URL + "service/menu";
        /**
         * 获取管控号的菜单激活列表（1.3.6加入）
         */
        public static final String URL_CHILD_MENNU = WEIDU_BASE_URL + "child/menu";
        /**
         * 绑定服务号
         */
        public static final String URL_COMPANY_SERVICE_BIND = WEIDU_BASE_URL + "service/bind";

        /**
         * 对文章点赞
         */
        public static final String URL_COMPANY_FIRST_LIKE = WEIDU_BASE_URL + "set/first/like";
        /**
         * 查询当前文章是否可以被评论、点赞数、评论数
         */
        public static final String URL_COMPANY_ARTICLE_PROPERTY = WEIDU_BASE_URL + "article/property";
        /**
         * 取消绑定服务号
         */
        public static final String URL_COMPANY_SERVICE_BINDDEL = WEIDU_BASE_URL + "service/bindDel";
        /**
         * 话题/创意收集/运营任务详情
         */
        public static final String URL_COMPANY_ACTIVE = WEIDU_BASE_URL + "company/active";
        /**
         * 参与话题/创意收集/运营任务
         */
        public static final String URL_COMPANY_ACTIVE_ATTEND = WEIDU_BASE_URL + "company/active/attend";
        /**
         * 文章详情
         */
        public static final String URL_COMPANY_ARTICLE = WEIDU_BASE_URL + "second/comment";
        /**
         * 关注服务号
         */
        public static final String URL_COMPAIN_SERVICE_RELATION = WEIDU_BASE_URL + "service/relation";
        /**
         * 订阅号列表
         */
        public static final String URL_COMPANIN_SUBSCRIBE_LIST = WEIDU_BASE_URL + "subscribe/lists";
        /**
         * 获取公众号公有消息列表
         */
        public static final String URL_GET_PUBLIC_MESSAGE = WEIDU_BASE_URL + "service/message/public";

        //===================== CreateApi ================================//
        //================DiscoveryApi=============//
        /**
         * 视频发现
         */
        public static final String URL_VIDEO_FIND = WEIDU_BASE_URL + "find";

        /**
         * 找资源
         */
        public static final String URL_RESOURCE_FIND = WEIDU_BASE_URL + "resources/resources";

        /**
         * 顶部banner（轮播图）
         */
        public static final String URL_RESOURCE_BANNER = WEIDU_BASE_URL + "resources/banner";

        /**
         * 标签列表
         */
        public static final String URL_RESOURCE_HOT_TAG = WEIDU_BASE_URL + "resources/tag";

        /**
         * 资源头部（TOP）
         */
        public static final String URL_RESOURCE_TOP = WEIDU_BASE_URL + "resources/top";

        /**
         * 产品列表
         */
        public static final String URL_PRODUCT_LIST = WEIDU_BASE_URL + "product/list";

        /**
         * 获取tag相关文章列表(活动列表)
         */
        public static final String URL_DISCOVERY_TAG_RESOURCES = WEIDU_BASE_URL + "resources/tag/resources";

        /**
         * 产品文章列表
         */
        public static final String URL_PRODUCT_ARTICLE_LIST = WEIDU_BASE_URL + "product/article/list";


        //================ExploreApi=============//
        /**
         * 成长日记首页接口（查询）
         */
        public static final String URL_DIARY_INDEX = WEIDU_BASE_URL + "diary/index";
        /**
         * 扫码关注产品（添加）
         */
        public static final String URL_SCAN_ADD = WEIDU_BASE_URL + "scan/add";

        public static final String url_scan_add_deivce = WEIDU_BASE_URL + "get/captcha";

        /**
         * 管理产品（查询）
         */
        public static final String URL_MANAGEMENT_LIST = WEIDU_BASE_URL + "management/index";
        /**
         * 管理产品（保存）
         */
        public static final String URL_MANAGEMENT_EDIT = WEIDU_BASE_URL + "management/edit";
        /**
         * 管理设备解绑
         */
        public static final String URL_MANAGEMENT_UNBIND = WEIDU_BASE_URL + "management/unbind";
        /**
         * 立即停止使用所有产品
         */
        public static final String URL_MANAGEMENT_SETALL = WEIDU_BASE_URL + "management/setall";

        /**
         * 剧情理念详情（查询）
         */
        public static final String URL_PLOT_DETAILS = WEIDU_BASE_URL + "plot/details";

        /**
         * 查询用户是否关联产品
         */
        public static final String URL_DIARY_APP = WEIDU_BASE_URL + "diary/app";

        /**
         * 查询对应游戏下的陪伴数据
         */
        public static final String URL_DIARY_DATA = WEIDU_BASE_URL + "diary/data";
        /**
         * 首页七条列表
         */
        public static final String URL_ARTICLE_INDEX = WEIDU_BASE_URL + "article/index";

        /**
         * 首页七条详情
         */
        public static final String URL_ARTICLE_DETAIL = WEIDU_BASE_URL + "article/detail";

        /**
         * 评论列表
         */
        public static final String URL_ARTICLE_COMMENT_LIST = WEIDU_BASE_URL + "article/comment/list";
        /**
         * version 1.3
         * 文章评论列表
         */
        public static final String URL_FIRST_COMMENT = WEIDU_BASE_URL + "first/comment";
        /**
         * version 1.3
         * 添加评论
         */
        public static final String URL_ARTICLE_COMMENT_ADD = WEIDU_BASE_URL + "set/first/comment";
        /**
         * version 1.3
         * 添加评论
         */
        public static final String URL_COMMENT_COMMENT_ADD = WEIDU_BASE_URL + "set/second/comment";

        /**
         * version 1.3
         * 添加评论
         */
        public static final String URL_ADD_ARTICLE_COMMENT = WEIDU_BASE_URL + "article/comment/add";
        /**
         * V1.3
         * 删除文章评论
         */
        public static final String URL_DELETE_COMMENT = WEIDU_BASE_URL + "del/comment";
        /**
         * 删除评论
         */
        public static final String URL_ARTICLE_COMMENT_DELETE = WEIDU_BASE_URL + "article/comment/delete";
        /**
         * 添加赞
         */
        public static final String URL_LIKE_ADD = WEIDU_BASE_URL + "article/like/add";

        /**
         * V1.3
         * 添加赞
         */
        public static final String URL_ARTICLE_LIKE_ADD = WEIDU_BASE_URL + "set/second/like";
    }

}
