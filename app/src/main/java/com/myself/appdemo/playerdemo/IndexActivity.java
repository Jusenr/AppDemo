package com.myself.appdemo.playerdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.myself.appdemo.R;

/**
 * Copyright @2015 上海杰图软件技术有限公司 <http://www.jietusoft.com>
 * 运行环境需求：Android 4.1（API level 16）及其以上版本
 * UtoVR全景播放集成
 * 1.必要的res
 * SDK所需的资源文件，请查看UtoVRSDK\lib\res目录。包括values\ids.xml、anim\loading.xml、以及loading所
 * 需图片（drawable-hdpi和drawable-xhdpi两个目录下），开发者可以修改资源内容（anim\loading.xml的内容、
 * drawable-hdpi和drawable-xhdpi两个目录下图片）但不可改变资源名称（values\ids.xml的文件名以及里面的
 * load_icon、anim\loading.xml的文件名），如果开发者重新建立工程或将UtoVR播放器集成到自己工程，请将该目
 * 录下所有文件拷贝到工程对应目录下。
 * 2.libs
 * SDK的库文件（so和jar文件），请查看UtoVRSDK\lib\libs目录。将libs下所有文件拷贝到工程libs下对应目录
 * tips:
 * .so文件导入，在build文件中添加处理，请参照UtoVRPlayerDemo对应处理：build文件中
 * task nativeLibsToJar以及tasks.withType(JavaCompile)
 * 3.权限
 * android.permission.WAKE_LOCK
 * android.permission.WRITE_EXTERNAL_STORAGE
 * android.permission.READ_EXTERNAL_STORAGE
 * android.permission.INTERNET
 * android.permission.ACCESS_NETWORK_STATE
 * android.permission.ACCESS_WIFI_STATE
 * android.permission.READ_PHONE_STATE
 * 4.opengl版本
 * 在AndroidManifest.xml中添加：
 * uses-feature
 * android:glEsVersion="0x00020000"
 * android:required="true"
 * 5.混淆
 * -dontwarn com.google.**
 * -dontwarn android.media.**
 * -dontwarn com.utovr.**
 * -keepattributes InnerClasses, Signature, *Annotation*
 * -keep class com.utovr.** {*;}
 * -keep class com.google.** {*;}
 * -keep class android.media.** {*;}
 * 具体参考Demo程序中proguard-rules.pro文件
 * 注意：
 * 设置播放路径setSource(UVMediaType type, String path)时，务必设置文件格式
 */

public class IndexActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        findViewById(R.id.btnByActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, PlayerActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btnByFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, PlayerFragmentAcivity.class);
                startActivity(intent);
            }
        });
    }
}
