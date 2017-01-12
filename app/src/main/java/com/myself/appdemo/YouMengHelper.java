package com.myself.appdemo;

import android.content.Context;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2017/1/12 11:53.
 */

public class YouMengHelper {
    /**
     * 。。。。
     */
    public static final String Scan_action = "Scan_action";


    /**----------------------------------分割线--------------------------------------*/

    /**
     * 记录打点数据
     */
    public static void onEvent(Context context, String name, String tag) {
//        MobclickAgent.onEvent(context, name, tag);
    }

    /**
     * 记录打点数据
     */
    public static void onEvent(Context context, String name) {
//        MobclickAgent.onEvent(context, name);
    }
}
