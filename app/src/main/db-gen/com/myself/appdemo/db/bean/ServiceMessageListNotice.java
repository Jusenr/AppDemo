package com.myself.appdemo.db.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by riven_chris on 16/6/17.
 */
public class ServiceMessageListNotice implements Serializable {

    /**
     * template_id : ff89383aee0b4c10a1eb655aec7a7424e51cd81b
     * url :
     * data : {"top_DATA1":"1","top_DATA2":"1","top_DATA3":"1","content_DATA1":"1","content_DATA2":"1","content_DATA3":"1","footer_DATA1":"1"}
     */

    private String template_id;
    private String url;
    private String location_action;
    private JSONObject data;

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getLocation_action() {
        return location_action;
    }

    public void setLocation_action(String location_action) {
        this.location_action = location_action;
    }
}
