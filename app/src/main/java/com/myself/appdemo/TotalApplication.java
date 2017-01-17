package com.myself.appdemo;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.myself.appdemo.db.DataBaseManager;
import com.myself.appdemo.db.dao.DaoMaster;
import com.myself.appdemo.db.dbmanager.CityDBManager;
import com.myself.appdemo.db.dbmanager.CompanionDBManager;
import com.myself.appdemo.db.dbmanager.DistrictDBManager;
import com.myself.appdemo.db.dbmanager.MessageDBMangaer;
import com.myself.appdemo.db.dbmanager.PaibandDBManager;
import com.myself.appdemo.db.dbmanager.ProvinceDBManager;
import com.myself.appdemo.db.dbmanager.TemplateDBManager;
import com.myself.appdemo.retrofit.api.BaseApi;
import com.myself.mylibrary.BasicApplication;
import com.myself.mylibrary.controller.ActivityManager;
import com.myself.mylibrary.util.AppUtils;
import com.myself.mylibrary.util.Logger;
import com.myself.mylibrary.util.PreferenceUtils;
import com.myself.mylibrary.util.SDCardUtils;
import com.youku.player.YoukuPlayerBaseConfiguration;

import java.io.File;

import im.fir.sdk.FIR;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2016/12/27 16:05.
 */

public class TotalApplication extends BasicApplication {
    public static final String FIR_API_TOKEN = "1b91eb3eaaea5f64ed127882014995dd";
    private static DaoMaster.OpenHelper mHelper;
    public static YoukuPlayerBaseConfiguration mYoukuPlayerBaseConfiguration;
    public static String resourcePath;

    /**
     * 网络环境切换
     *
     * @return
     */
    @Override
    protected boolean configEnvironment() {
        //初始化Service Api
        BaseApi.init(BaseApi.HOST_FORMAL);

        return BaseApi.isDebug();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //安装数据库
        installDataBase();
        //Fir-SDk配置
        FIR.init(this);
        //初始化优酷播放器
        initYoukuPlayer();
        //资源路径
        resourcePath = sdCardPath + File.separator + "patch";
        //初始化地址和emojs资源
        startService(new Intent(this, ResourceInitService.class));
    }

    @Override
    protected String getBuglyKey() {
        return null;
    }

    @Override
    protected String getLogFilePath() {
        return sdCardPath + File.separator + "appdemolog.log";
    }

    @Override
    public String getPackageName() {
        return "com.myself.appdemo";
    }

    @Override
    protected String getLogTag() {
        return "app_demo";
    }

    @Override
    protected String getNetworkCacheDirectoryPath() {
        return sdCardPath + File.separator + "http_cache";
    }

    @Override
    protected String getADSdCardPath() {
        return SDCardUtils.getSDCardPath() + File.separator + getLogTag();
    }

    @Override
    protected int getNetworkCacheSize() {
        return 20 * 1024 * 1024;//20MB
    }

    @Override
    protected int getNetworkCacheMaxAgeTime() {
        return 0;
    }

    @Override
    public void singleSign(String msg) {

    }

    @Override
    public String appDeviceId() {
        String deviceId = PreferenceUtils.getValue(Constants.SPKey.PREFERENCE_KEY_DEVICE_ID, "");
        boolean isLogin = false;
        if (TextUtils.isEmpty(deviceId)) {
            if (isLogin) {
                deviceId = AppUtils.getRealDeviceId(this);
                Log.d("appDeviceId", "deviceId-->: " + deviceId);
            } else {
                deviceId = AppUtils.getRealDeviceId(this) + System.currentTimeMillis();
            }
        }
        PreferenceUtils.save(Constants.SPKey.PREFERENCE_KEY_DEVICE_ID, deviceId);
        return deviceId;
    }

    /**
     * 初始化优酷播放器
     */
    private void initYoukuPlayer() {
        final String mDownloadPath = sdCardPath + File.separator + "videocache";
        try {
            mYoukuPlayerBaseConfiguration = new YoukuPlayerBaseConfiguration(getApplicationContext()) {
                /**
                 * 通过覆写该方法，返回“正在缓存视频信息的界面”，
                 * 则在状态栏点击下载信息时可以自动跳转到所设定的界面.
                 * 用户需要定义自己的缓存界面
                 */
                @Override
                public Class<? extends Activity> getCachingActivityClass() {
                    return MainActivity.class;
                }

                /**
                 * 通过覆写该方法，返回“已经缓存视频信息的界面”，
                 * 则在状态栏点击下载信息时可以自动跳转到所设定的界面.
                 * 用户需要定义自己的已缓存界面
                 */
                @Override
                public Class<? extends Activity> getCachedActivityClass() {
                    return MainActivity.class;
                }

                /**
                 * 配置视频的缓存路径，格式举例： /appname/videocache/
                 * 如果返回空，则视频默认缓存路径为： /应用程序包名/videocache/
                 */
                @Override
                public String configDownloadPath() {
                    return mDownloadPath;
                }
            };
        } catch (Exception e) {
            Logger.e(e);
            e.printStackTrace();
        }
    }

    /**
     * 获取DataBaseManager
     *
     * @param clazz 类型
     * @return DataBaseManager实例
     */
    public static DataBaseManager getDataBaseManager(Class<? extends DataBaseManager> clazz) {
        switch (clazz.getSimpleName()) {
            case "CityDBManager":
                return CityDBManager.getInstance(mHelper);
            case "DistrictDBManager":
                return DistrictDBManager.getInstance(mHelper);
            case "ProvinceDBManager":
                return ProvinceDBManager.getInstance(mHelper);
            case "CompanionDBManager":
                return CompanionDBManager.getInstance(mHelper);
            case "TemplateDBManager":
                return TemplateDBManager.getInstance(mHelper);
            case "PaibandDBManager":
                return PaibandDBManager.getInstance(mHelper);
            case "MessageDBMangaer":
                return MessageDBMangaer.getInstance(mHelper);
        }
        return null;
    }

    /**
     * 安装数据库
     */
    private void installDataBase() {
        String DBName = "myself_appdemo.db";
        //删除旧数据库
        File file = new File(" /data/data/" + AppUtils.getPackageName(this) + "/database/myself_appdemo.db");
        if (file != null && file.exists())
            file.delete();
        if (mHelper == null)
            if (isDebug) {
//                String DBPath = getSdCardPath() + File.separator + DBName;
                mHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), DBName, null);
            } else
                mHelper = new DaoMaster.OpenHelper(getApplicationContext(), DBName, null) {
                    @Override
                    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                        Log.d("database", "oldVersion: " + oldVersion + " newVersion: " + newVersion);
                    }
                };
    }

    /**
     * 捕捉到异常就退出App
     *
     * @param ex 异常信息
     */
    @Override
    protected void onCrash(Throwable ex) {
        Logger.e("APP崩溃了,错误信息是" + ex.getMessage());
        ex.printStackTrace();

        ActivityManager.getInstance().finishAllActivity();
    }

}
