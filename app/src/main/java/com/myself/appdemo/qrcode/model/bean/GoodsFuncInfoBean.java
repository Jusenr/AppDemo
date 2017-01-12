package com.myself.appdemo.qrcode.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 激活产品信息
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2016/10/13 10:35.
 */

public class GoodsFuncInfoBean implements Serializable {
    /**
     * "goods_id": 1,
     * "appid": 1100,//产品的appid
     * "service_id": 123, //对应之前版本的服务号，保留不用
     * "app_tools": [],//应用工具
     * "goods_name": "Hello编程",
     * "icon": "http://weidu.file.dev.putaocloud.com/file/d6f8c358be5cf69530bf58e17952a778e27e1b11.png",
     * "is_active": 0, //是否激活，
     * "is_bluetooth": 1, //是否有蓝牙功能，0或1
     * "operation_type":1, //产品详情右上角的操作类型0为无，1协助登录，2解除绑定
     * "goods_guide": "?goods_id=2",//激活引导页数据
     * "banner": [],
     * "menu": []
     */
    private String goods_id;
    private String appid;//----------------------产品的appid
    private String service_id;//-----------------对应之前版本的服务号，保留不用
    private List<FunctionBean> app_tools;//------应用工具
    private String goods_name;
    private String icon;
    private String is_active;//------------------是否激活，
    private String is_bluetooth;//---------------是否有蓝牙功能，0或1
    private String operation_type;//-------------操作类型0为无，1协助登录，2解除绑定(产品详情右上角的)
    private String goods_guide;//----------------激活引导页数据
    private List<GoodsBannerBean> banner;//------banner列表
    private List<FunctionBean> menu;//-----------功能列表

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public List<FunctionBean> getApp_tools() {
        return app_tools;
    }

    public void setApp_tools(List<FunctionBean> app_tools) {
        this.app_tools = app_tools;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getIs_bluetooth() {
        return is_bluetooth;
    }

    public void setIs_bluetooth(String is_bluetooth) {
        this.is_bluetooth = is_bluetooth;
    }

    public String getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }

    public String getGoods_guide() {
        return goods_guide;
    }

    public void setGoods_guide(String goods_guide) {
        this.goods_guide = goods_guide;
    }

    public List<GoodsBannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<GoodsBannerBean> banner) {
        this.banner = banner;
    }

    public List<FunctionBean> getMenu() {
        return menu;
    }

    public void setMenu(List<FunctionBean> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "GoodsFuncInfoBean{" +
                "goods_id='" + goods_id + '\'' +
                ", appid='" + appid + '\'' +
                ", service_id='" + service_id + '\'' +
                ", app_tools=" + app_tools +
                ", goods_name='" + goods_name + '\'' +
                ", icon='" + icon + '\'' +
                ", is_active='" + is_active + '\'' +
                ", is_bluetooth='" + is_bluetooth + '\'' +
                ", operation_type='" + operation_type + '\'' +
                ", goods_guide=" + goods_guide +
                ", banner=" + banner +
                ", menu=" + menu +
                '}';
    }

    public class FunctionBean implements Serializable {
        /**
         * "name": "视频",
         * "icon" : "http://weidu.file.dev.putaocloud.com/file/74a547db5b0971b9b17e28fe4ad191b8f5880bdd.png",
         * "location_type": "local",
         * "location_action": "action_9",
         * "is_active":"0",
         * "sort": "2",
         * "unactivated_prompt":"请激活paibot"
         */
        private String name;
        private String icon;
        private String location_type;
        private String location_action;
        private String is_active;
        private String unactivated_prompt;
        private String sort;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getLocation_type() {
            return location_type;
        }

        public void setLocation_type(String location_type) {
            this.location_type = location_type;
        }

        public String getLocation_action() {
            return location_action;
        }

        public void setLocation_action(String location_action) {
            this.location_action = location_action;
        }

        public String getIs_active() {
            return is_active;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
        }

        public String getUnactivated_prompt() {
            return unactivated_prompt;
        }

        public void setUnactivated_prompt(String unactivated_prompt) {
            this.unactivated_prompt = unactivated_prompt;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        @Override
        public String toString() {
            return "FunctionBean{" +
                    "name='" + name + '\'' +
                    ", icon='" + icon + '\'' +
                    ", location_type='" + location_type + '\'' +
                    ", location_action='" + location_action + '\'' +
                    ", is_active='" + is_active + '\'' +
                    ", unactivated_prompt='" + unactivated_prompt + '\'' +
                    ", sort='" + sort + '\'' +
                    '}';
        }
    }

    public class GoodsBannerBean implements Serializable {
        /**
         * "media_type": "image",//媒体类型 image、video、h5
         * "cover": "http://weidu.file.dev.putaocloud.com/file/780789dda3071e8dce6b907b7f239ce4649b73b1.jpg", //显示的封面
         * "url": null, //如果为video时此字段为视频地址或优酷id
         * "location_type": "local",
         * "location_action": "action_9"
         */
        private String media_type;//--------------媒体类型 image或video
        private String cover;//-------------------显示的封面
        private String url;//---------------------如果为video时此字段为视频地址或优酷id
        private String location_type;
        private String location_action;

        public String getMedia_type() {
            return media_type;
        }

        public void setMedia_type(String media_type) {
            this.media_type = media_type;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLocation_type() {
            return location_type;
        }

        public void setLocation_type(String location_type) {
            this.location_type = location_type;
        }

        public String getLocation_action() {
            return location_action;
        }

        public void setLocation_action(String location_action) {
            this.location_action = location_action;
        }

        @Override
        public String toString() {
            return "GoodsBannerBean{" +
                    "media_type='" + media_type + '\'' +
                    ", cover='" + cover + '\'' +
                    ", url='" + url + '\'' +
                    ", location_type='" + location_type + '\'' +
                    ", location_action='" + location_action + '\'' +
                    '}';
        }
    }
}
