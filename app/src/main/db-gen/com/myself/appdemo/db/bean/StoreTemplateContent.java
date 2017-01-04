package com.myself.appdemo.db.bean;

import java.io.Serializable;
import java.util.List;

/**
 * title : {"value":"您已成功完成订购"}
 * top : [{"value":"{{~top_DATA1~}}"},{"value":"{{~top_DATA2~}}","position":"center"},{"value":"{{~top_DATA3~}}","position":"center","font-size":"large","font-weight":"bold"}]
 * content : [{"value":"支付方式：{{~content_DATA1~}}"},{"value":"商品详情：{{~content_DATA2~}}"},{"value":"订单号：{{~content_DATA3~}}"}]
 * footer : [{"value":"{{~footer_DATA1~}}"}]
 * bottom : {"value":"查看详情"}
 * <p/>
 * Created by riven_chris on 16/6/20.
 */
public class StoreTemplateContent implements Serializable {

    /**
     * value : 您已成功完成订购
     */

    private TemplateTitle title;
    /**
     * value : 查看详情
     */

    private TemplateBottom bottom;
    /**
     * value : {{~top_DATA1~}}
     */

    private List<TemplateTop> top;
    /**
     * value : 支付方式：{{~content_DATA1~}}
     */

    private List<TemplateContent> content;
    /**
     * value : {{~footer_DATA1~}}
     */

    private List<TemplateFooter> footer;

    private List<TemplateCover> cover;

    public List<TemplateCover> getCover() {
        return cover;
    }

    public void setCover(List<TemplateCover> cover) {
        this.cover = cover;
    }

    public TemplateTitle getTitle() {
        return title;
    }

    public void setTitle(TemplateTitle title) {
        this.title = title;
    }

    public TemplateBottom getBottom() {
        return bottom;
    }

    public void setBottom(TemplateBottom bottom) {
        this.bottom = bottom;
    }

    public List<TemplateTop> getTop() {
        return top;
    }

    public void setTop(List<TemplateTop> top) {
        this.top = top;
    }

    public List<TemplateContent> getContent() {
        return content;
    }

    public void setContent(List<TemplateContent> content) {
        this.content = content;
    }

    public List<TemplateFooter> getFooter() {
        return footer;
    }

    public void setFooter(List<TemplateFooter> footer) {
        this.footer = footer;
    }

    public static class TemplateTitle implements Serializable {
        private String value;
        private String position;
        private String fontSize;
        private String fontWeight;
        private String fontColor;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getFontSize() {
            return fontSize;
        }

        public void setFontSize(String fontSize) {
            this.fontSize = fontSize;
        }

        public String getFontWeight() {
            return fontWeight;
        }

        public void setFontWeight(String fontWeight) {
            this.fontWeight = fontWeight;
        }

        public String getFontColor() {
            return fontColor;
        }

        public void setFontColor(String fontColor) {
            this.fontColor = fontColor;
        }
    }

    public static class TemplateBottom implements Serializable {
        private String value;
        private String position;
        private String fontSize;
        private String fontWeight;
        private String fontColor;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getFontSize() {
            return fontSize;
        }

        public void setFontSize(String fontSize) {
            this.fontSize = fontSize;
        }

        public String getFontWeight() {
            return fontWeight;
        }

        public void setFontWeight(String fontWeight) {
            this.fontWeight = fontWeight;
        }

        public String getFontColor() {
            return fontColor;
        }

        public void setFontColor(String fontColor) {
            this.fontColor = fontColor;
        }
    }

    public static class TemplateTop implements Serializable {
        private String value;
        private String position;
        private String fontSize;
        private String fontWeight;
        private String fontColor;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getFontSize() {
            return fontSize;
        }

        public void setFontSize(String fontSize) {
            this.fontSize = fontSize;
        }

        public String getFontWeight() {
            return fontWeight;
        }

        public void setFontWeight(String fontWeight) {
            this.fontWeight = fontWeight;
        }

        public String getFontColor() {
            return fontColor;
        }

        public void setFontColor(String fontColor) {
            this.fontColor = fontColor;
        }
    }

    public static class TemplateContent implements Serializable {
        private String value;
        private String position;
        private String fontSize;
        private String fontWeight;
        private String fontColor;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getFontSize() {
            return fontSize;
        }

        public void setFontSize(String fontSize) {
            this.fontSize = fontSize;
        }

        public String getFontWeight() {
            return fontWeight;
        }

        public void setFontWeight(String fontWeight) {
            this.fontWeight = fontWeight;
        }

        public String getFontColor() {
            return fontColor;
        }

        public void setFontColor(String fontColor) {
            this.fontColor = fontColor;
        }
    }

    public static class TemplateFooter implements Serializable {
        private String value;
        private String position;
        private String fontSize;
        private String fontWeight;
        private String fontColor;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getFontSize() {
            return fontSize;
        }

        public void setFontSize(String fontSize) {
            this.fontSize = fontSize;
        }

        public String getFontWeight() {
            return fontWeight;
        }

        public void setFontWeight(String fontWeight) {
            this.fontWeight = fontWeight;
        }

        public String getFontColor() {
            return fontColor;
        }

        public void setFontColor(String fontColor) {
            this.fontColor = fontColor;
        }
    }

    public static class TemplateCover implements Serializable {
        private String value;
        private String position;
        private String fontSize;
        private String fontWeight;
        private String fontColor;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getFontSize() {
            return fontSize;
        }

        public void setFontSize(String fontSize) {
            this.fontSize = fontSize;
        }

        public String getFontWeight() {
            return fontWeight;
        }

        public void setFontWeight(String fontWeight) {
            this.fontWeight = fontWeight;
        }

        public String getFontColor() {
            return fontColor;
        }

        public void setFontColor(String fontColor) {
            this.fontColor = fontColor;
        }
    }
}
