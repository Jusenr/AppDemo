package com.myself.appdemo.qrcode.model.bean;

import java.io.Serializable;

/**
 * 扫码流水号对应的业务数据
 * Created by zsw on 2016/7/12.
 */
public class SerialNumData implements Serializable {

    private String account_name;
    private String avatar;
    private String code;
    private String device_id;
    private String login_appid;
    private String login_callback;
    private String login_server_id;
    private String uid;
    private String weidu_callback;
    private String weidu_service_id;
    private String chat_id;

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getLogin_appid() {
        return login_appid;
    }

    public void setLogin_appid(String login_appid) {
        this.login_appid = login_appid;
    }

    public String getLogin_callback() {
        return login_callback;
    }

    public void setLogin_callback(String login_callback) {
        this.login_callback = login_callback;
    }

    public String getLogin_server_id() {
        return login_server_id;
    }

    public void setLogin_server_id(String login_server_id) {
        this.login_server_id = login_server_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWeidu_callback() {
        return weidu_callback;
    }

    public void setWeidu_callback(String weidu_callback) {
        this.weidu_callback = weidu_callback;
    }

    public String getWeidu_service_id() {
        return weidu_service_id;
    }

    public void setWeidu_service_id(String weidu_service_id) {
        this.weidu_service_id = weidu_service_id;
    }
}
