package com.myself.appdemo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.myself.appdemo.db.DataBaseManager;
import com.myself.appdemo.db.dao.DaoMaster;
import com.myself.appdemo.db.dbmanager.CityDBManager;
import com.myself.appdemo.db.dbmanager.CompanionDBManager;
import com.myself.appdemo.db.dbmanager.DistrictDBManager;
import com.myself.appdemo.db.dbmanager.MessageDBMangaer;
import com.myself.appdemo.db.dbmanager.PaibandDBManager;
import com.myself.appdemo.db.dbmanager.ProvinceDBManager;
import com.myself.appdemo.db.dbmanager.TemplateDBManager;
import com.myself.mylibrary.BasicApplication;
import com.myself.mylibrary.controller.ActivityManager;
import com.myself.mylibrary.util.AppUtils;
import com.myself.mylibrary.util.Logger;
import com.myself.mylibrary.util.PreferenceUtils;
import com.myself.mylibrary.util.SDCardUtils;
import com.myself.mylibrary.view.image.ImagePipelineConfigFactory;

import java.io.File;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2016/12/27 16:05.
 */

public class TotalApplication extends BasicApplication {
    private static DaoMaster.OpenHelper mHelper;

    public static String resourcePath;

    /**
     * 网络环境切换
     *
     * @return
     */
    @Override
    protected boolean configEnvironment() {

        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //安装数据库
        installDataBase();
        //Fresco初始化
        Fresco.initialize(getApplicationContext(),
                ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(getApplicationContext(), getOkHttpClient()));
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
        return sdCardPath + File.separator + "log";
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
    protected String getSdCardPath() {
        return SDCardUtils.getSDCardPath() + File.separator + getLogTag();
    }

    @Override
    protected String getNetworkCacheDirectoryPath() {
        Log.e("####", sdCardPath + File.separator + "http_cache");
        return sdCardPath + File.separator + "http_cache";
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
        if (file != null && file.exists()) file.delete();
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
