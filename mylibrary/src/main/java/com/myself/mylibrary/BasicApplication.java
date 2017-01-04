package com.myself.mylibrary;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.myself.mylibrary.http.OkHttpManager;
import com.myself.mylibrary.http.interceptor.CacheStrategyInterceptor;
import com.myself.mylibrary.http.interceptor.HeaderInfoInterceptor;
import com.myself.mylibrary.http.interceptor.NetworkInterceptor;
import com.myself.mylibrary.http.interceptor.ResponseInfoInterceptor;
import com.myself.mylibrary.util.AppUtils;
import com.myself.mylibrary.util.CrashHandler;
import com.myself.mylibrary.util.DiskFileCacheHelper;
import com.myself.mylibrary.util.Logger;
import com.myself.mylibrary.util.hawk.Hawk;
import com.myself.mylibrary.util.hawk.LogLevel;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

/**
 * 基础Application
 * Created by guchenkai on 2015/11/19.
 */
public abstract class BasicApplication extends MultiDexApplication {
    private static final String KEY_APP_ID = "app_id";
    private static Context mContext;
    private static OkHttpClient mOkHttpClient;//OkHttpClient
    private static int maxAge;//网络缓存最大时间

    private static DiskFileCacheHelper mDiskFileCacheHelper;//磁盘文件缓存器

    public static String app_id;
    public static String app_device_id;
    public static boolean isDebug;
    public static String sdCardPath;//SdCard路径
    public static Map<String, String> emojis;//表情包映射

    public static Map<String, String> getEmojis() {
        return emojis;
    }

    public static void setEmojis(Map<String, String> emojis) {
        BasicApplication.emojis = emojis;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isDebug = configEnvironment();
        mContext = getApplicationContext();
        //sdCard缓存路径
        sdCardPath = getSdCardPath();
        //ButterKnife的Debug模式
        ButterKnife.setDebug(isDebug);
        //偏好设置文件初始化
        Hawk.init(getApplicationContext(), getPackageName(), isDebug ? LogLevel.FULL : LogLevel.FULL);
        //日志输出
        Logger.init(getLogTag()).hideThreadInfo().setLogLevel(isDebug ? Logger.LogLevel.FULL : Logger.LogLevel.FULL)
                .setSaveLog(true);
        //OkHttp初始化
        mOkHttpClient = OkHttpManager.getInstance(getNetworkCacheDirectoryPath(), getNetworkCacheSize())
                .addInterceptor(new NetworkInterceptor())
                .addInterceptor(new ResponseInfoInterceptor())
                .addInterceptor(new CacheStrategyInterceptor())
                .addInterceptor(new HeaderInfoInterceptor(AppUtils.getVersionName(mContext)))
                .build();

        //开启bugly
//        CrashReport.initCrashReport(getApplicationContext(), getBuglyKey(), isDebug);
        //网络缓存最大时间
        maxAge = getNetworkCacheMaxAgeTime();
        //磁盘文件缓存器
        mDiskFileCacheHelper = DiskFileCacheHelper.get(getApplicationContext(), getLogTag());
        //数据库调试
        QueryBuilder.LOG_SQL = isDebug;
        QueryBuilder.LOG_VALUES = isDebug;
        //app_id配置
        app_id = AppUtils.getMetaData(getApplicationContext(), KEY_APP_ID);
        app_device_id = appDeviceId();
        //捕捉系统崩溃异常
        CrashHandler.instance().init(new CrashHandler.OnCrashHandler() {
            @Override
            public void onCrashHandler(Throwable ex) {
                onCrash(ex);
            }
        });
    }

    public static Context getInstance() {
        return mContext;
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static int getMaxAge() {
        return maxAge;
    }

    public static DiskFileCacheHelper getDiskFileCacheHelper() {
        return mDiskFileCacheHelper;
    }

    /**
     * 设置腾讯bugly的AppKey
     *
     * @return 腾讯bugly的AppKey
     */
    protected abstract String getBuglyKey();

    /**
     * debug模式
     *
     * @return 是否开启
     */
    protected abstract boolean configEnvironment();

    /**
     * 填写工程包名
     *
     * @return 工程包名
     */
    public abstract String getPackageName();

    /**
     * 设置调试日志标签名
     *
     * @return 调试日志标签名
     */
    protected abstract String getLogTag();

    /**
     * 设置sdCard路径
     *
     * @return sdCard路径
     */
    protected abstract String getSdCardPath();

    /**
     * 网络缓存文件夹路径
     *
     * @return 缓存文件夹路径
     */
    protected abstract String getNetworkCacheDirectoryPath();

    /**
     * 网络缓存文件大小
     *
     * @return 缓存文件大小
     */
    protected abstract int getNetworkCacheSize();

    /**
     * 网络缓存最大时间
     *
     * @return 缓存最大时间
     */
    protected abstract int getNetworkCacheMaxAgeTime();

    /**
     * 异常信息处理
     *
     * @param ex 异常信息
     */
    protected abstract void onCrash(Throwable ex);

    /**
     * 单点登录
     *
     * @param msg
     */
    public abstract void singleSign(String msg);

    /**
     * 生成供应用使用的deviceId
     */
    public abstract String appDeviceId();
}
