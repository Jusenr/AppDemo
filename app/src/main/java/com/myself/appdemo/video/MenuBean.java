package com.myself.appdemo.video;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 视频数据源
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2017/1/17 15:43.
 */
public class MenuBean implements Serializable {

    /**
     * data : [{"menu_icon":"http://weidu.file.dev.putaocloud.com/file/186b390f98631eff68c9de4be316131f545041a9.jpg","menu_name":"管控","menu_video":"XMTU3MzY4MzkwMA=="},{"menu_icon":"http://weidu.file.dev.putaocloud.com/file/af77c3955aaaca26c37e1d49633231c5948a8048.jpg","menu_name":"成长","menu_video":"XMTU3MzY4MTA2OA=="},{"menu_icon":"http://weidu.file.dev.putaocloud.com/file/f0d95c38b1285a940523f59e6c2d09b6f47f9c5f.jpg","menu_name":"陪伴","menu_video":"XMTU3MzY4MzAwNA=="},{"menu_icon":"http://weidu.file.dev.putaocloud.com/file/186b390f98631eff68c9de4be316131f545041a9.jpg","menu_name":"哈泥海洋","menu_video":"XMTQ1MzIzODAwNA=="}]
     * elapsed : 2
     * http_code : 200
     * msg :
     */

    private int elapsed;
    private int http_code;
    private String msg;
    private List<DataBean> data;

    public int getElapsed() {
        return elapsed;
    }

    public void setElapsed(int elapsed) {
        this.elapsed = elapsed;
    }

    public int getHttp_code() {
        return http_code;
    }

    public void setHttp_code(int http_code) {
        this.http_code = http_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * menu_icon : http://weidu.file.dev.putaocloud.com/file/186b390f98631eff68c9de4be316131f545041a9.jpg
         * menu_name : 管控
         * menu_video : XMTU3MzY4MzkwMA==
         */

        private String menu_icon;
        private String menu_name;
        private String menu_video;

        public String getMenu_icon() {
            return menu_icon;
        }

        public void setMenu_icon(String menu_icon) {
            this.menu_icon = menu_icon;
        }

        public String getMenu_name() {
            return menu_name;
        }

        public void setMenu_name(String menu_name) {
            this.menu_name = menu_name;
        }

        public String getMenu_video() {
            return menu_video;
        }

        public void setMenu_video(String menu_video) {
            this.menu_video = menu_video;
        }
    }

    public static final String menu_json = "{\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"menu_icon\": \"http://weidu.file.dev.putaocloud.com/file/186b390f98631eff68c9de4be316131f545041a9.jpg\",\n" +
            "            \"menu_name\": \"管控\",\n" +
            "            \"menu_video\": \"XMTQ1MzIzODAwNA==\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"menu_icon\": \"http://weidu.file.dev.putaocloud.com/file/af77c3955aaaca26c37e1d49633231c5948a8048.jpg\",\n" +
            "            \"menu_name\": \"成长\",\n" +
            "            \"menu_video\": \"XMTQ1MzIzNjc4NA==\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"menu_icon\": \"http://weidu.file.dev.putaocloud.com/file/f0d95c38b1285a940523f59e6c2d09b6f47f9c5f.jpg\",\n" +
            "            \"menu_name\": \"陪伴\",\n" +
            "            \"menu_video\": \"XMTQ1MzI0MDI5Mg==\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"menu_icon\": \"http://weidu.file.dev.putaocloud.com/file/186b390f98631eff68c9de4be316131f545041a9.jpg\",\n" +
            "            \"menu_name\": \"哈泥海洋\",\n" +
            "            \"menu_video\": \"XMTQ1MzIzODgxNg==\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"elapsed\": 2,\n" +
            "    \"http_code\": 200,\n" +
            "    \"msg\": \"\"\n" +
            "}";
}
