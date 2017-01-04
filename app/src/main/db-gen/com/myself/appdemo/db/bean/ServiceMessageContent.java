package com.myself.appdemo.db.bean;

import java.io.Serializable;

/**
 * Created by zhanghao on 2015/12/8.
 */
public class ServiceMessageContent implements Serializable {
    private String article_id;
    private String title;
    private String sub_title;
    private String cover_pic;
    private String link_url;
    private int release_time;

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public int getRelease_time() {
        return release_time;
    }

    public void setRelease_time(int release_time) {
        this.release_time = release_time;
    }

    @Override
    public String toString() {
        return "ServiceMessageContent{" +
                "article_id='" + article_id + '\'' +
                ", title='" + title + '\'' +
                ", sub_title='" + sub_title + '\'' +
                ", cover_pic=" + cover_pic +
                ", link_url='" + link_url + '\'' +
                ", release_time=" + release_time +
                '}';
    }
}
