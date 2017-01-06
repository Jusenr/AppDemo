package com.myself.appdemo.api;

import com.myself.appdemo.base.PTWDRequestHelper;
import com.myself.appdemo.retrofit.api.BaseApi;
import com.myself.mylibrary.http.request.RequestMethod;

import okhttp3.Request;

/**
 * Created by Administrator on 2016/4/7.
 */
public class CollectionApi {
    private static final String COLLECTION_PAGE = "page";//页码
    private static final String QUESTION_DETAIL = "question";//反馈的意见
    private static final String CONTACT_WAY = "contact_way";//联系方式

    /**
     * 我的收藏
     */
    public static Request getCollection(int page) {
        return PTWDRequestHelper.find()
                .addParam(COLLECTION_PAGE, page + "")
                .build(RequestMethod.POST, BaseApi.Url.URL_USER_COLLECTS);
    }

    /**
     * 意见反馈
     */
    public static Request submitAdvice(String advice, String contact_way) {
        return PTWDRequestHelper.find()
                .addParam(QUESTION_DETAIL, advice)
                .addParam(CONTACT_WAY, contact_way)
                .build(RequestMethod.GET, BaseApi.Url.URL_USER_ADVICE);
    }
}
