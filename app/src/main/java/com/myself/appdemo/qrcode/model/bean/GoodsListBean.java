package com.myself.appdemo.qrcode.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 产品列表
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2016/10/8 18:14.
 */

public class GoodsListBean implements Serializable {
    /**
     * "goods_list": [],
     * "type_name": "科技产品"
     */
    private String type_name;//---------------------产品类型(PAI系列、科技产品...)
    private List<GoodsInfoBean> goods_list;//-------产品列表

    private String type;//(自定义属性) active | unactivated 默认不传则返回全部产品 active=已激活 unactivated=未激活

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public List<GoodsInfoBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsInfoBean> goods_list) {
        this.goods_list = goods_list;
    }

    @Override
    public String toString() {
        return "GoodsListBean{" +
                "type_name='" + type_name + '\'' +
                ", goods_list=" + goods_list +
                ", type='" + type + '\'' +
                '}';
    }

    public class GoodsInfoBean implements Serializable {
        /**
         * "goods_id": 1,
         * "appid": 1100,
         * "service_id": 123,
         * "goods_name": "Hello编程",
         * "goods_icon": "http://weidu.file.dev.putaocloud.com/file/d6f8c358be5cf69530bf58e17952a778e27e1b11.png",,
         * "goods_cover": "http://weidu.file.dev.putaocloud.com/file/74a547db5b0971b9b17e28fe4ad191b8f5880bdd.png",
         * "is_active": 0
         * "is_bluetooth": 1,
         * "operation_type": 0
         */
        private String goods_id;
        private String appid;
        private String service_id;
        private String goods_name;
        private String goods_icon;
        private String goods_cover;
        private String is_active;
        private String is_bluetooth;
        private String operation_type;

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

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_icon() {
            return goods_icon;
        }

        public void setGoods_icon(String goods_icon) {
            this.goods_icon = goods_icon;
        }

        public String getGoods_cover() {
            return goods_cover;
        }

        public void setGoods_cover(String goods_cover) {
            this.goods_cover = goods_cover;
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

        @Override
        public String toString() {
            return "GoodsInfoBean{" +
                    "goods_id='" + goods_id + '\'' +
                    ", appid='" + appid + '\'' +
                    ", service_id='" + service_id + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", goods_icon='" + goods_icon + '\'' +
                    ", goods_cover='" + goods_cover + '\'' +
                    ", is_active='" + is_active + '\'' +
                    ", is_bluetooth='" + is_bluetooth + '\'' +
                    ", operation_type='" + operation_type + '\'' +
                    '}';
        }
    }
}
