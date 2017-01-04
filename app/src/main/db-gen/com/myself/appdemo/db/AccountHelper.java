package com.myself.appdemo.db;

import com.alibaba.fastjson.JSONObject;

/**
 * 通行证助手
 * Created by guchenkai on 2015/11/25.
 */
public final class AccountHelper {

    public static String SP_COMPANION_CURRENT_CHILD_INFO = "sp_companion_current_child_info_";

    /**
     * 登录
     *
     * @param jsonObject jsonObject
     */
    public static void login(JSONObject jsonObject) {
        String uid = jsonObject.getString("uid");
        String token = jsonObject.getString("token");
        String nickname = jsonObject.getString("nickname");
        String mobile = jsonObject.getString("mobile");
        String expire_time = jsonObject.getString("expire_time");
        String refresh_token = jsonObject.getString("refresh_token");
    }

    /**
     * 获取当前登录的Uid
     *
     * @return 当前登录的Uid
     */
    public static String getCurrentUid() {
        return "1229";
    }


    /**
     * 获取当前的token
     *
     * @return 当前登录的Uid
     */
    public static String getCurrentToken() {
        return "1312436547697";
    }

    /**
     * 获取当前昵称
     *
     * @return
     */
    public static String getUserNickName() {
        return "jusenr";
    }

    /**
     * 获取当前登录的手机号
     *
     * @return 当前登录的手机号
     */
    public static String getCurrentMobile() {
        return "15091159762";
    }

    /**
     * 获取当前的孩子ID
     */
    public static String getCurrentChildId() {
        return "123456";
    }

    /**
     * 根据关系名转关系id
     *
     * @return
     */
    public static String setIdentity(String relation) {
        String relationship = "";
        switch (relation) {
            case "爸爸":
                relationship = "1";
                break;
            case "妈妈":
                relationship = "2";
                break;
            case "爷爷":
                relationship = "3";
                break;
            case "奶奶":
                relationship = "4";
                break;
            case "外公":
                relationship = "5";
                break;
            case "外婆":
                relationship = "6";
                break;
            default:
                relationship = "7";
        }
        return relationship;
    }

    /**
     * 根据关系名id转关系名
     *
     * @return
     */
    public static String getIdentity(String relationship) {
        String relation = "";
        switch (relationship) {
            case "1":
                relation = "爸爸";
                break;
            case "2":
                relation = "妈妈";
                break;
            case "3":
                relation = "爷爷";
                break;
            case "4":
                relation = "奶奶";
                break;
            case "5":
                relation = "外公";
                break;
            case "6":
                relation = "外婆";
                break;
            case "7":
                relation = "其他";
                break;
            default:
                relation = "其他";
        }
        return relation;
    }
}
