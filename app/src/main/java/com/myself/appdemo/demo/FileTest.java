package com.myself.appdemo.demo;

import com.myself.mylibrary.util.SDCardUtils;

import java.io.File;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Email      : jusenr@163.com
 * Company    : 葡萄科技
 * Author     : Jusenr
 * Date       : 2017/1/10 10:17.
 */

public class FileTest {

    public static void main(String[] args) {
        String sdCardPath = SDCardUtils.getSDCardPath() + File.separator + "aapp_demo";

    }
}
