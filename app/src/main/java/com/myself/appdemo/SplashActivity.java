package com.myself.appdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.myself.mylibrary.controller.BasicFragmentActivity;
import com.myself.mylibrary.util.AnimationUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends BasicFragmentActivity {
    private final int SPLASH_DISPLAY_LENGHT = 5000; //延迟5秒

    @BindView(R.id.splash)
    ImageView mSplash;

    @Override
    protected int getLayoutId() {
        getWindow().requestFeature(Window.FEATURE_PROGRESS); //去标题栏
        return R.layout.activity_splash;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        AlphaAnimation alphaAnimation = AnimationUtils.getShowAlphaAnimation(1400);
        ScaleAnimation scaleAnimation = AnimationUtils.getAmplificationAnimation(1200);
        RotateAnimation rotateAnimation = AnimationUtils.getRotateAnimationByCenter(1000);
        AnimationSet set = AnimationUtils.getAnimationSet();
        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(rotateAnimation);
        mSplash.setAnimation(set);
        set.startNow();

//        mSplash.setAnimation(rotateAnimation);

        new Handler().postDelayed(new Runnable() {
            // 为了减少代码使用匿名Handler创建一个延时的调用
            public void run() {
                onStartClick();
            }
        }, SPLASH_DISPLAY_LENGHT);   //5秒，够用了吧
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick(R.id.btn_start)
    public void onStartClick() {
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        //通过Intent打开最终真正的主界面Main这个Activity
        startActivity(i);    //启动Main界面
        finish();    //关闭自己这个开场屏
    }
}
