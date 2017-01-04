package com.myself.mylibrary.http.callback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.myself.mylibrary.util.JsonUtils;
import com.myself.mylibrary.util.Logger;
import com.myself.mylibrary.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * FastJson解析json
 * Created by guchenkai on 2015/10/27.
 */
abstract class FastJsonCallback<T extends Serializable> extends JSONObjectCallback {
    private Class<? extends Serializable> clazz;

    public FastJsonCallback(Class<? extends Serializable> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public final void onSuccess(String url, JSONObject result) {
        String data = result.getString("data");
        String httpCode = result.getString("http_code");
        String msg = result.getString("msg");
        if (!StringUtils.isEmpty(data) && String.class.equals(clazz)) {
            onSuccess(url, (T) data);
            return;
        } else if (StringUtils.isEmpty(data) && StringUtils.equals(httpCode, "200")) {
            onSuccess(url, (T) null);
            return;
        } else if (StringUtils.isEmpty(data) && StringUtils.equals(httpCode, "4000")) {
            onFinish(url, false, msg != null ? msg : "");
            return;
        } else if (StringUtils.isEmpty(data) && !StringUtils.equals(httpCode, "200")) {
            onFinish(url, false, msg != null ? msg : "");
            return;
        }
        if (StringUtils.equals(data, "[]") && !StringUtils.equals(getGenericClassName(), ArrayList.class.getName()))
            data = null;
        JsonUtils.JsonType type = JsonUtils.getJSONType(data);
        switch (type) {
            case JSON_TYPE_OBJECT:
                onSuccess(url, (T) JSON.parseObject(data, clazz));
                break;
            case JSON_TYPE_ARRAY:
                onSuccess(url, (T) JSON.parseArray(data, clazz));
                break;
            case JSON_TYPE_ERROR:
                onFailure(url, -200, "data数据返回错误");
                Logger.e(JSONObjectCallback.TAG, "result=" + result.toJSONString());
                break;
        }
    }

    @Override
    public final void onCacheSuccess(String url, JSONObject result) {
        String data = result.getString("data");
        if (!StringUtils.isEmpty(data) && String.class.equals(clazz)) {
            onCacheSuccess(url, (T) data);
            return;
        } else if (StringUtils.isEmpty(data) && StringUtils.equals(result.getString("http_code"), "200")) {
            onCacheSuccess(url, (T) new String(""));
            return;
        } else if (StringUtils.isEmpty(data) && !StringUtils.equals(result.getString("http_code"), "200")) {
            onFinish(url, false, result.getString("msg") != null ? result.getString("msg") : "");
            return;
        }
        if (StringUtils.equals(data, "[]") && !StringUtils.equals(getGenericClassName(), ArrayList.class.getName()))
            data = null;
        JsonUtils.JsonType type = JsonUtils.getJSONType(data);
        switch (type) {
            case JSON_TYPE_OBJECT:
                onCacheSuccess(url, (T) JSON.parseObject(data, clazz));
                break;
            case JSON_TYPE_ARRAY:
                onCacheSuccess(url, (T) JSON.parseArray(data, clazz));
                break;
            case JSON_TYPE_ERROR:
                onFailure(url, -200, "data数据返回错误");
                Logger.e(JSONObjectCallback.TAG, "result=" + result.toJSONString());
                break;
        }
    }

    /**
     * 获取本类的泛型类型
     *
     * @return 泛型类型
     */
    private String getGenericClassName() {
        Type genType = this.getClass().getGenericSuperclass();
        Type generic = ((ParameterizedType) genType).getActualTypeArguments()[0];
        if (!(generic instanceof Class))
            try {
                Field mRawTypeName = generic.getClass().getDeclaredField("rawTypeName");
                mRawTypeName.setAccessible(true);
                return (String) mRawTypeName.get(generic);
            } catch (Exception e) {
                Logger.e("获取泛型类型错误.", e);
            }
        return "";
    }

    /**
     * 网络请求成功回调
     *
     * @param url    网络地址
     * @param result 请求结果
     */
    public abstract void onSuccess(String url, T result);

    /**
     * 缓存请求成功回调
     *
     * @param url    网络地址
     * @param result 请求结果
     */
    public abstract void onCacheSuccess(String url, T result);
}
