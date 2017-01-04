package com.myself.mylibrary.http;

import com.myself.mylibrary.BasicApplication;
import com.myself.mylibrary.R;
import com.myself.mylibrary.http.callback.DownloadCallback;
import com.myself.mylibrary.http.callback.RequestCallback;
import com.myself.mylibrary.http.request.FormEncodingRequestBuilder;
import com.myself.mylibrary.http.request.RequestMethod;
import com.myself.mylibrary.util.FileUtils;
import com.myself.mylibrary.util.Logger;
import com.myself.mylibrary.util.ResourcesUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 普通网络请求助手
 * Created by guchenkai on 2016/1/22.
 */
public class OkHttpRequestHelper {
    private static final String TAG = OkHttpRequestHelper.class.getSimpleName();

    private int mCacheType;
    private OkHttpClient mOkHttpClient;

    public OkHttpRequestHelper() {
        if (mOkHttpClient == null)
            mOkHttpClient = BasicApplication.getOkHttpClient();
        mCacheType = CacheType.NETWORK_ELSE_CACHE;//默认是先请求网络,再请求缓存
    }

    public static OkHttpRequestHelper newInstance() {
        return new OkHttpRequestHelper();
    }

    /**
     * 设置缓存策略
     *
     * @param cacheType 缓存策略
     * @return OkHttpFormEncodingHelper实例
     */
    public OkHttpRequestHelper cacheType(int cacheType) {
        mCacheType = cacheType;
        return this;
    }

    /**
     * 添加拦截器
     *
     * @param interceptor 拦截器
     * @return OkHttpFormEncodingHelper实例
     */
    public OkHttpRequestHelper addInterceptor(Interceptor interceptor) {
        mOkHttpClient.networkInterceptors().add(interceptor);
        return this;
    }

    /**
     * 添加拦截器
     *
     * @param interceptors 拦截器组
     * @return OkHttpFormEncodingHelper实例
     */
    public OkHttpRequestHelper addInterceptors(List<Interceptor> interceptors) {
        mOkHttpClient.networkInterceptors().addAll(interceptors);
        return this;
    }

    /**
     * 网络请求
     *
     * @param request  请求实例
     * @param callback 请求回调
     */
    private void requestFromNetwork(Request request, Callback callback) {
        Logger.d(TAG, "读取网络信息,Url=" + getUrl(request));
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 缓存请求
     *
     * @param request  请求实例
     * @param callback 请求回调
     */
    private void requestFromCache(Request request, RequestCallback callback) {
        Response response = getResponse(mOkHttpClient.cache(), request);
        //暂时使用此方式返回
        Call call = mOkHttpClient.newCall(request);
        if (response != null)
            try {
                Logger.d(TAG, "读取缓存信息,Url=" + getUrl(request));
                callback.onCacheResponse(response);
            } catch (IOException e) {
                callback.onFailure(call, e);
                Logger.e(e);
            }
        else
            callback.onFailure(call, new IOException(ResourcesUtils.getString(BasicApplication.getInstance(), R.string.not_cache)));
    }

    /**
     * 反射方法取得响应体
     *
     * @param cache   缓存
     * @param request 请求体
     * @return 响应体
     */
    private Response getResponse(Cache cache, Request request) {
        try {
            Class clz = cache.getClass();
            Method get = clz.getDeclaredMethod("get", Request.class);
            get.setAccessible(true);
            return (Response) get.invoke(cache, request);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(e);
        }
        return null;
    }

    /**
     * 请求
     *
     * @param request  请求实例
     * @param callback 请求回调
     */
    public void request(final Request request, final RequestCallback callback) {
        if (callback == null)
            throw new NullPointerException("请设置请求回调");
        //如果不是缓存请求,执行OnStart方法
        if (mCacheType == CacheType.NETWORK || mCacheType == CacheType.NETWORK_ELSE_CACHE)
            callback.onStart();
        switch (mCacheType) {
            case CacheType.NETWORK:
                requestFromNetwork(request, callback);
                break;
            case CacheType.CACHE:
                requestFromCache(request, callback);
                break;
            case CacheType.CACHE_ELSE_NETWORK:
                requestFromCache(request, new RequestCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }

                    @Override
                    public void onCacheResponse(Response response) throws IOException {
                        if (response.isSuccessful())
                            callback.onCacheResponse(response);
                        else
                            requestFromNetwork(request, callback);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        requestFromNetwork(call.request(), callback);
                    }
                });
                break;
            case CacheType.NETWORK_ELSE_CACHE:
                requestFromNetwork(request, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        requestFromCache(call.request(), callback);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful())
                            callback.onResponse(call, response);
                        else
                            requestFromCache(request, callback);
                    }
                });
                break;
        }
    }

    /**
     * 获取Url
     *
     * @param request 请求体
     * @return url
     */
    private String getUrl(Request request) {
        return request.url().toString();
    }

    /**
     * 下载文件
     *
     * @param url      下载地址
     * @param filePath 保存文件的路径
     * @param callback 下载文件回调
     */
    public void download(String url, String filePath, DownloadCallback callback) {
        callback.onStart();
        callback.setFilePath(filePath);
        if (FileUtils.isExists(filePath)) {
            callback.onFinish(url, true, "现在文件已存在,请不要重复下载");
            return;
        }
        mOkHttpClient.newCall(FormEncodingRequestBuilder.newInstance()
                .build(RequestMethod.GET, url)).enqueue(callback);
    }

    /**
     * 取消请求
     *
     * @param url url
     */
    public void cancelRequest(String url) {
//        mOkHttpClient.;
    }
}
