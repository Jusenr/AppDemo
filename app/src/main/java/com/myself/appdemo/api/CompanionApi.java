package com.myself.appdemo.api;

import com.myself.appdemo.TotalApplication;
import com.myself.appdemo.base.PTWDRequestHelper;
import com.myself.appdemo.db.AccountHelper;
import com.myself.appdemo.retrofit.api.BaseApi;
import com.myself.mylibrary.http.request.RequestMethod;
import com.myself.mylibrary.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * 创造接口
 * Created by zhanghao on 2015/1/28.
 */
public class CompanionApi {
    private static final String REQUEST_CAPTCHA_TOKEN = "captcha_token";//扫描受控设备二维码获取的参数
    private static final String REQUEST_PRODUCT_ID = "product_id";//游戏应用产品ID
    private static final String REQUEST_PAGE = "page";//页码，没传默认为第一页
    private static final String REQUEST_ARTICLE_ID = "article_id";//文章id
    private static final String REQUEST_CID = "cid";//如果cid＝0 操作文章  如果cid != 0, 操作用户to_uid
    private static final String REQUEST_TO_UID = "to_uid";//操作用户id
    private static final String REQUEST_COMMENT = "comment";//作品内容
    private static final String REQUEST_PICS = "pics";//作品图片，多图
    private static final String REQUEST_SERVICE_ID = "service_id";//服务号id
    private static final String REQUEST_MESSAGE_ID = "message_id";//对应消息的push_id
    private static final String REQUEST_TEMPLATE_ID = "template_id";//商城推送模板id
    private static final String REQUEST_SEND_DATA = "send_data";//接收推送的数据包，转发给纬度服务器
    private static final String REQUEST_LAST_PULL_ID = "last_pull_id";//最后拉取id，每10条返回，连续多次拉取将最后拉取的id继续传入请求
    private static final String REQUEST_URL = "url";
    private static final String REQUEST_RWD_MID = "wd_mid";//点赞文章ID
    private static final String SERVICE_ID = "sid"; //服务号唯一service_id
    private static final String REQUEST_TYPE = "type";
    private static final String REQUEST_LINK_URL = "link_url";
    private static final String REQUEST_OPEN_ID = "open_id";//用户与公众号的唯一ID
    private static final String REQUEST_MESSAGE = "message";//提问内容

    private static final String REQUEST_PARENT_UID = "parent_uid";//家长UID
    private static final String REQUEST_CHILD_UID = "child_uid";//子账号uid
    private static final String REQUEST_ADMIN_UID = "admin_uid";//超级管理员uid
    private static final String REQUEST_HANDLE_UID = "handle_uid";//待处理的uid
    private static final String REQUEST_HANDLE_TYPE = "handle_type";//处理操作
    private static final String REQUEST_SIGN = "sign";//签名
    private static final String REQUEST_CHILD_ID = "child_id";//孩子UID

    private static final String CHAT_ID = "chat_id";
    private static final String ACTION = "action";//操作
    private static final String PUBLIC_KEY = "public_key";//公钥
    private static final String SECRETKEY = "a59036fcbd864f6bacc51296df710e59";
    private static final String PUBLICKEY = "fd4b62a8b8f04d6d8a7dd5fc33df61c8";

    /**
     * 设备管理(需登录)
     *
     * @param captcha_token 扫描受控设备二维码获取的参数
     */
    public static Request addScan(String captcha_token) {
        return PTWDRequestHelper.start()
                .addParam(REQUEST_CAPTCHA_TOKEN, captcha_token)
                .build(RequestMethod.POST, BaseApi.Url.URL_ADD_SCAN);
    }

    private static final String MESSAGE = "message";//首页评论内容
    private static final String ARTICLE_ID = "article_id";//首页文章ID
    private static final String COMMENT_ID = "comment_id";//首页评论ID



    private static final String REQUEST_COMMENT_WD_MID = "wd_mid";//一级评论的wd_mid
    private static final String REQUEST_COMMENT_SID = "sid";//服务号唯一service_id
    private static final String REQUEST_COMMENT_PCID = "pcid";//一级评论内的comment_id
    private static final String REQUEST_COMMENT_PAGE = "page";//页数
    private static final String REQUEST_COMMENT_SN = "sn";//

    /**
     * 校验设备序列号并返回设备信息
     */
    public static Request qrServerHandler(String chat_id) {
        Map<String, String> params = new HashMap<>();
        params.put(CHAT_ID, chat_id);
        params.put(ACTION, "get_qr_info");
        params.put(PUBLIC_KEY, PUBLICKEY);
        params.put(PTWDRequestHelper.REQUEST_KEY_UID, AccountHelper.getCurrentUid());
        params.put(PTWDRequestHelper.REQUEST_KEY_TOKEN, AccountHelper.getCurrentToken());
        params.put(PTWDRequestHelper.REQUEST_KEY_APP_ID, TotalApplication.app_id);
        String sign = StringUtils.generateSign(params, SECRETKEY);
        return PTWDRequestHelper.find()
                .addParam(CHAT_ID, chat_id)
                .addParam(ACTION, "get_qr_info")
                .addParam(PUBLIC_KEY, PUBLICKEY)
                .addParam(REQUEST_SIGN, sign)
                .build(RequestMethod.POST, BaseApi.Url.QR_SERVER_HANDLER);
    }
}
