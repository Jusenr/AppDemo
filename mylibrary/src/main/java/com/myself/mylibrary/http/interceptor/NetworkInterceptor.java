package com.myself.mylibrary.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 判断是否有网络拦截器
 * Created by sunnybear on 16/1/18.
 */
public class NetworkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
//        if (!NetworkUtils.isNetworkReachable(BasicApplication.getInstance())) {
//            ToastUtils.showToastLong(BasicApplication.getInstance(),
//                    BasicApplication.getInstance().getResources().getString(R.string.not_network));
//            return null;
//        }
        return chain.proceed(chain.request());
    }
}
