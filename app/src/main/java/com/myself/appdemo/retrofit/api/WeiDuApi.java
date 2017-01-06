package com.myself.appdemo.retrofit.api;

import com.myself.appdemo.retrofit.RetrofitBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by luowentao on 2016/8/1.
 */
public interface WeiDuApi {
    /**
     * 刷新用户关联的孩子数据（添加、删除孩子时）
     */
    @GET(BaseApi.Url.URL_CHILD_REFRESH)
    Observable<RetrofitBean<List<String>>> childRefresh(@QueryMap Map<String, String> map);

}
