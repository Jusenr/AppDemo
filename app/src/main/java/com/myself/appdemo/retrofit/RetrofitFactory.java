package com.myself.appdemo.retrofit;

import com.myself.appdemo.TotalApplication;
import com.myself.appdemo.retrofit.api.BaseApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by riven_chris on 16/7/3.
 */
public class RetrofitFactory {

    public static Retrofit getWeiDuRetrofit() {
        return WeiDuRetrofitPlaceHolder.RETROFIT;
    }

    public static Retrofit getQRRetrofit() {
        return QRRetrofitPlaceHolder.RETROFIT;
    }

    public static class WeiDuRetrofitPlaceHolder {
        static final Retrofit RETROFIT = build(BaseApi.WEIDU_BASE_URL);
    }

    public static class QRRetrofitPlaceHolder {
        static final Retrofit RETROFIT = build(BaseApi.QRCODE_BASE_URL);
    }

    public static Retrofit build(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(TotalApplication.getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
