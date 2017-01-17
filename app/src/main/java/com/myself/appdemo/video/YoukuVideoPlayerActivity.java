package com.myself.appdemo.video;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myself.appdemo.R;
import com.myself.mylibrary.controller.BasicFragmentActivity;
import com.myself.mylibrary.util.Logger;
import com.myself.mylibrary.util.StringUtils;
import com.myself.mylibrary.util.ToastUtils;
import com.youku.player.ApiManager;
import com.youku.player.VideoQuality;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;

import butterknife.BindView;


/**
 * 优酷视频播放器
 * Created by guchenkai on 2016/1/14.
 */
public class YoukuVideoPlayerActivity extends BasicFragmentActivity {
    public static final String BUNDLE_VID = "vid";
    public static final String BUNDLE_LOCAL_VID = "local_vid";
    public static final String BUNDLE_IS_FROM_LOCAL = "is_from_local";

    @BindView(R.id.fl_player)
    YoukuPlayerView fl_player;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.rl_bottom_bar)
    RelativeLayout rlBottomBar;
    @BindView(R.id.tv_buy)
    TextView tvBuy;

    private YoukuBasePlayerManager mBasePlayerManager;
    // 需要播放的视频id
    private String vid;
    // YoukuPlayer实例，进行视频播放控制
    private YoukuPlayer mPlayer;
    // 标示是否播放的本地视频
    private boolean isFromLocal = false;
    // 需要播放的本地视频的id
    private String local_vid;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_youku_video;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        ivClose.setOnClickListener(onCloseClickListener);
        tvBuy.setOnClickListener(onBuyClickListener);
        mBasePlayerManager = new YoukuBasePlayerManager(this) {
            @Override
            public void setPadHorizontalLayout() {

            }

            @Override
            public void onInitializationSuccess(YoukuPlayer player) {
                // 初始化成功后需要添加该行代码
                addPlugins();
                //实例化YoukuPlayer实例
                mPlayer = player;
                //开始播放
                goPlay();
//                setSharpness(VideoQuality.STANDARD);//设置清晰度(高清)
                fl_player.getIMediaPlayerDelegate().goSmall();
            }

            @Override
            public void onFullscreenListener() {
                ivClose.setVisibility(View.GONE);
                rlBottomBar.setVisibility(View.GONE);
            }

            @Override
            public void onSmallscreenListener() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ivClose.setVisibility(View.VISIBLE);
//                        if (mBean != null) {
//                            rlBottomBar.setVisibility(View.VISIBLE);
//                        } else {
//                            rlBottomBar.setVisibility(View.GONE);
//                        }
                    }
                }, 500);
            }
        };
        mBasePlayerManager.onCreate();
        getIntentData();
        if (StringUtils.isEmpty(vid)) {
            Logger.d("视频vid为空");
            ToastUtils.showToastShort(this, "很抱歉，当前还没有相关视频");
            onBackPressed();
        }
        // 控制竖屏和全屏时候的布局参数。这两句必填。
        fl_player.setSmallScreenLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        fl_player.setFullScreenLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        try {
            //初始化播放器相关数据
            fl_player.initialize(mBasePlayerManager);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToastShort(this, "播放器初始化失败！");
            finish();
            return;
        }

        //点击视频返回
        fl_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private View.OnClickListener onBuyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            YoukuVideoPlayerActivity.this.finish();
        }
    };

    private View.OnClickListener onCloseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            YoukuVideoPlayerActivity.this.finish();
        }
    };

    @Override
    public void onBackPressed() {
        Logger.d("onBackPressed before super");
        super.onBackPressed();
        Logger.d("onBackPressed");
        mBasePlayerManager.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mBasePlayerManager.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBasePlayerManager.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        if (mBasePlayerManager.shouldCallSuperKeyDown())
            return super.onKeyDown(keyCode, event);
        else
            return mBasePlayerManager.onKeyDown(keyCode, event);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mBasePlayerManager.onLowMemory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBasePlayerManager.onPause();
//        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBasePlayerManager.onResume();
    }

    @Override
    public boolean onSearchRequested() {
        return mBasePlayerManager.onSearchRequested();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBasePlayerManager.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBasePlayerManager.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getIntentData();
        goPlay();
    }

    /**
     * 设置清晰度
     */
    private void setSharpness(VideoQuality quality) {
        try {
            // 通过ApiManager实例更改清晰度设置，返回值（1):成功；（0): 不支持此清晰度
            // 接口详细信息可以参数使用文档
            int result = ApiManager.getInstance().changeVideoQuality(quality, mBasePlayerManager);
            if (result == 0)
                Toast.makeText(mContext, "不支持此清晰度", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取上个页面传递过来的数据
     */
    private void getIntentData() {
        // 判断是不是本地视频
        isFromLocal = args.getBoolean(BUNDLE_IS_FROM_LOCAL, false);
        if (isFromLocal) {  // 播放本地视频
            local_vid = args.getString(BUNDLE_LOCAL_VID);
        } else { // 在线播放
            vid = args.getString(BUNDLE_VID);
        }
    }

    /**
     * 开始播放
     */
    private void goPlay() {
        if (isFromLocal)
            mPlayer.playLocalVideo(local_vid);
        else
            mPlayer.playVideo(vid);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
