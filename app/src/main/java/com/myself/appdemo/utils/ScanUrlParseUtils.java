package com.myself.appdemo.utils;

import android.net.Uri;

import com.myself.appdemo.TotalApplication;
import com.myself.appdemo.db.AccountHelper;
import com.myself.mylibrary.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 扫描url解析工具
 * Created by guchenkai on 2015/12/23.
 */
public class ScanUrlParseUtils {

    /**
     * 获得scheme
     *
     * @param scanUrl 扫描出的url
     * @return Scheme
     */
    public static String getScheme(String scanUrl) {
        return scanUrl.substring(0, scanUrl.indexOf(":"));
    }

    /**
     * 获得url
     *
     * @param scanUrl 扫描出的url
     * @return url
     */
    public static String getUrl(String scanUrl) {
        return scanUrl.substring(scanUrl.indexOf(":") + 3, scanUrl.length());
    }

    /**
     * 获得可请求url
     *
     * @param scanUrl 扫描出的url
     * @return 可请求url
     */
    public static String getRequestUrl(String scanUrl) {
        StringBuffer sb = new StringBuffer(getUrl(scanUrl));
        sb.append("&appid=").append(TotalApplication.app_id)
                .append("&token=").append(AccountHelper.getCurrentToken());
        return sb.toString();
    }

    /**
     * 获得添加设备请求url
     *
     * @param scanUrl 扫描出的url
     * @return 可请求url
     */
    public static String getDeviceRequestUrl(String scanUrl) {
        return getUrl(scanUrl);
    }

    /**
     * 获取参数
     *
     * @param url
     * @return
     */
    public static Map<String, String> getParams(String url) {
        Map<String, String> params = new HashMap<>();
        String[] paramters = url.substring(url.indexOf("?") + 1, url.length()).split("&");
        for (String param : paramters) {
            String[] ps = param.split("=");
            if (ps != null && ps.length > 1) {
                params.put(ps[0], ps[1]);
            }
        }
        return params;
    }

    /**
     * 从url里面获取某个参数
     *
     * @param url
     * @param par
     * @return
     */
    public static String getSingleParams(String url, String par) {
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(par)) return "";
        String value = Uri.parse(url).getQueryParameter(par);
        if (value == null) value = "";
        return value;
    }
    public static String getDeviceSerialNum(String scanStr) {
        return scanStr.substring(scanStr.indexOf("?")+4,scanStr.indexOf("&"));
    }

    /**
     * 二维码扫描的scheme
     */
    public static final class Scheme {
        public static final String PUTAO_LOGIN = "ptlogin";
        public static final String PUTAO_DEVICE = "ptdevicecontrol";
        public static final String HTTP = "http";
        public static final String PICO_ACCOUNT_SERVER = "pico-account-server";
        public static final String PT_BROWSER_LOGIN= "pt-browser-login";
        public static final String BLUTOOTH = "bluetooth";
        public static final String PT_CHILD = "ptchild";
    }
}
