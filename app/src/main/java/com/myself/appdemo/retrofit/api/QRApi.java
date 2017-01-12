package com.myself.appdemo.retrofit.api;

import com.myself.appdemo.qrcode.model.bean.CommonBindResponse;
import com.myself.appdemo.qrcode.model.bean.QRProductInfo;
import com.myself.appdemo.retrofit.RetrofitBean;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by riven_chris on 2016/12/16.
 */

public interface QRApi {

    @FormUrlEncoded
    @POST(BaseApi.Url.URL_QR_SERVER_HANDLER)
    Observable<QRProductInfo> getQRServerHandler(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<RetrofitBean<String>> getSerialNumData(@Url String url, @Field("data") String data);

    @GET
    Observable<CommonBindResponse> scanLogin(@Url String url);
}
