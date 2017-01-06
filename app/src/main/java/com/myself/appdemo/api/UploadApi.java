package com.myself.appdemo.api;

import com.myself.appdemo.db.AccountHelper;
import com.myself.appdemo.retrofit.api.BaseApi;
import com.myself.mylibrary.http.UploadFileTask;
import com.myself.mylibrary.http.callback.UploadCallback;
import com.myself.mylibrary.http.request.MultiPartRequestBuilder;

import java.io.File;

import okhttp3.Request;

/**
 * 上传文件接口
 * Created by guchenkai on 2015/12/14.
 */
public class UploadApi {
    public static final String REQUEST_UID = "x:uid";
    public static final String REQUEST_FILENAME = "filename";
    public static final String REQUEST_SHA1 = "sha1";
    public static final String REQUEST_TYPE = "type";
    public static final String REQUEST_APP_ID = "appid";
    public static final String REQUEST_UPLOAD_TOKEN = "uploadToken";
    public static final String REQUEST_FILE = "file";

    private static final String UPLOAD_APP_ID = "1003";//上传使用的app_id

    public static void install(String base_url) {
//        FILE_BASE_URL = base_url;
    }

    /**
     * 校检sha1
     *
     * @param sha1 sha1
     */
    public static Request checkSha1(String sha1) {
        return MultiPartRequestBuilder.newInstance()
                .addParam(REQUEST_SHA1, sha1)
                .build(BaseApi.Url.URL_ADD_SCAN);
    }

    /**
     * 上传文件
     *
     * @param uploadToken 上传token
     * @param file        上传文件
     * @return
     */
    public static void uploadFile(String uploadToken, String sha1, File file, UploadCallback callback) {
        UploadFileTask.newInstance()
                .addParam(REQUEST_APP_ID, UPLOAD_APP_ID)
                .addParam(REQUEST_UPLOAD_TOKEN, uploadToken)
                .addParam(REQUEST_UID, AccountHelper.getCurrentUid())
                .addParam(REQUEST_FILENAME, sha1)
                .addParam(REQUEST_SHA1, sha1)
                .addParam(REQUEST_FILE, file)
                .build(BaseApi.Url.URL_ARTICLE_INDEX, callback);
    }
}
