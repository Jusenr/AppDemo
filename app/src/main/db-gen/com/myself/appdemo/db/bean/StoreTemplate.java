package com.myself.appdemo.db.bean;

import java.io.Serializable;

/**
 * Created by riven_chris on 16/6/20.
 */
public class StoreTemplate implements Serializable {


    /**
     * template_id : ff89383aee0b4c10a1eb655aec7a7424e51cd81b
     * title : 您已成功完成订购
     * template_content : {"title":{"value":"您已成功完成订购"},"top":[{"value":"{{~top_DATA1~}}"},{"value":"{{~top_DATA2~}}","position":"center"},{"value":"{{~top_DATA3~}}","position":"center","font-size":"large","font-weight":"bold"}],"content":[{"value":"支付方式：{{~content_DATA1~}}"},{"value":"商品详情：{{~content_DATA2~}}"},{"value":"订单号：{{~content_DATA3~}}"}],"footer":[{"value":"{{~footer_DATA1~}}"}],"bottom":{"value":"查看详情"}}
     */

    private String template_id;
    private String title;
    private StoreTemplateContent template_content;

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StoreTemplateContent getTemplate_content() {
        return template_content;
    }

    public void setTemplate_content(StoreTemplateContent template_content) {
        this.template_content = template_content;
    }

}
