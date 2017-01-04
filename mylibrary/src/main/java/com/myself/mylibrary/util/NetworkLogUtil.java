package com.myself.mylibrary.util;

import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by Administrator on 2016/4/7.
 */
public class NetworkLogUtil {
    public static StringBuffer networkLog = new StringBuffer(1024 * 6);

    public static void addLog(Request request) {
        if (request == null)
            return;
        if (networkLog.length() > 1024 * 5)
            networkLog.delete(0, networkLog.length());
        StringBuffer requestSB = new StringBuffer();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        requestSB.append("\n");
        requestSB.append("请求  URL: " + request.url() + "\n");
        requestSB.append("请求  时间: " + format.format(new Date(System.currentTimeMillis())) + "\n");
        requestSB.append("请求  方法: " + request.method() + "\n");

        String headerStr = "请求头信息: ";
        if (request.headers() != null) {
            Headers headers = request.headers();
            Set<String> headerNames = headers.names();
            int i = 0;
            for (String name : headerNames) {
                headerStr += name + " : ";
                headerStr += headers.get(name);
                if (i < headerNames.size() - 1)
                    headerStr += "\n" + "           ";
                i++;
            }
            headerStr += "\n";
        }
        requestSB.append(headerStr);

        String bodyStr = "";
        if (request.body() != null) {
            String body = bodyToString(request);

            if (body != null && !"".equals(body)) {
                String[] params = body.split("&");
                int i = 0;
                for (String str : params) {
                    if (i < params.length - 1)
                        bodyStr += str + "\n" + "           ";
                }
            }
            bodyStr = "请求  参数: " + bodyStr;
            requestSB.append(bodyStr + "\n");
        }
        Log.e("NetWork", "networkRequest: " + requestSB.toString());
        networkLog.insert(0, requestSB.toString());
    }


    public static void addLog(Response response, String result) {
        if (response == null) return;
        if (networkLog.length() > 1024 * 512)
            networkLog.delete(0, networkLog.length());
        StringBuffer responseSB = new StringBuffer();
        responseSB.append("响应的请求: " + response.request().url().toString() + "\n");
        responseSB.append("响应  结果: " + (response.isSuccessful() ? "成功" : "失败") + "\n");
        responseSB.append("响应状态码: " + response.code() + "\n");
        String headerStr = "响应头信息: ";
        if (response.headers() != null) {
            Headers headers = response.headers();
            Set<String> headerNames = headers.names();
            int i = 0;
            for (String name : headerNames) {
                headerStr += name + " : ";
                headerStr += headers.get(name);
                if (i < headerNames.size() - 1)
                    headerStr += "\n" + "           ";
                i++;
            }
            headerStr += "\n";
        }
        responseSB.append(headerStr);

        if (result != null) {
            responseSB.append("响应  数据: " + result + "\n");
        }
        responseSB.append("-----------------------------------" + "\n\n");
        Log.e("NetWork", "networkRequest: " + responseSB.toString());
        networkLog.insert(0, responseSB.toString());
    }

    private static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return null;
        }
    }

}
