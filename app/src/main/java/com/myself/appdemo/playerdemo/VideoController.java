package com.myself.appdemo.playerdemo;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.myself.appdemo.R;

/**
 * Created by xilin on 2016/5/25.
 */
public class VideoController implements View.OnClickListener {
    private SeekBar skTime;                    // 播放进度条
    private TextView tvTime;                   // 时间长度
    private ImageView imgFullscreen;           // 全屏按钮
    private ToggleButton tbtnGyro;             // 陀螺仪控制按钮
    private ToggleButton tbtnDualScreen;       // 单双屏
    private ToggleButton tbtnPlayPause;        // 启动、暂停按钮
    private RelativeLayout rlPlayPro;          // 播放进度

    private String videoTimeString = null;     // 时间长度文本
    private PlayerControl player;

    private Handler CacheProHandler = null;    //网络播放，且不是m3u8时添加缓冲进度条
    private boolean changeOrientation;

    public VideoController(RelativeLayout toolbar, PlayerControl player, boolean changeOrientation) {
        this.player = player;
        this.changeOrientation = changeOrientation;
        skTime = (SeekBar) toolbar.findViewById(R.id.video_tool_Seekbar);                  // 进度
        tvTime = (TextView) toolbar.findViewById(R.id.video_tool_tvTime);                  // 时间
        imgFullscreen = (ImageView) toolbar.findViewById(R.id.video_tool_imgFullscreen);
        tbtnGyro = (ToggleButton) toolbar.findViewById(R.id.video_tool_tbtnGyro);          // 陀螺仪
        tbtnDualScreen = (ToggleButton) toolbar.findViewById(R.id.video_tool_tbtnVR);      // 单双屏
        tbtnPlayPause = (ToggleButton) toolbar.findViewById(R.id.video_tool_tbtnPlayPause);// 播放/暂停
        rlPlayPro = (RelativeLayout) toolbar.findViewById(R.id.video_tool_rlPlayProg);
        if (changeOrientation)
            imgFullscreen.setOnClickListener(this);
        skTime.setOnSeekBarChangeListener(mSeekBarChange);
        tbtnGyro.setOnClickListener(this);
        tbtnDualScreen.setOnClickListener(this);
        tbtnPlayPause.setOnClickListener(this);
    }

    private SeekBar.OnSeekBarChangeListener mSeekBarChange = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (player != null) {
                player.seekTo(seekBar.getProgress());
                player.toolbarTouch(false);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            if (player != null) {
                player.toolbarTouch(true);
            }
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_tool_imgFullscreen:// 是否全屏播放
                if (player != null) {
                    player.toFullScreen();
                }
                break;
            case R.id.video_tool_tbtnGyro:    // 陀螺仪
                if (player != null) {
                    player.setGyroEnabled(!player.isGyroEnabled());
                    tbtnGyro.setChecked(player.isGyroEnabled());
                }
                break;
            case R.id.video_tool_tbtnVR:  // 单双屏
                if (player != null) {
                    boolean isScreen = !player.isDualScreenEnabled();
                    player.setDualScreenEnabled(isScreen);
                    if (isScreen) {
                        player.setGyroEnabled(true);
                        tbtnGyro.setChecked(true);
                    }
                }
                break;
            case R.id.video_tool_tbtnPlayPause:// 播放/暂停
                if (((ToggleButton) v).isChecked()) {
                    player.pause();
                } else {
                    player.play();
                }
                break;
        }
    }

    public void setDualScreenEnabled(boolean val) {
        if (player != null)
            player.setDualScreenEnabled(val);
        tbtnDualScreen.setChecked(val);
    }

    public void updateBufferProgress() {
        if (player != null) {
            skTime.setSecondaryProgress((int) player.getBufferedPosition());
        }
    }

    public void updateCurrentPosition() {
        // 发出更新进度条的message
        handleProgress.sendEmptyMessage(0);
    }

    void changeOrientation(boolean isLandscape, int NavigationH) {
        if (!changeOrientation) {
            return;
        }
        if (isLandscape) {
            imgFullscreen.setVisibility(View.GONE);
            tbtnGyro.setVisibility(View.VISIBLE);
            tbtnDualScreen.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams playParams = (RelativeLayout.LayoutParams) rlPlayPro.getLayoutParams();
            playParams.addRule(RelativeLayout.LEFT_OF, R.id.video_tool_tbtnGyro);

            rlPlayPro.setLayoutParams(playParams);
            RelativeLayout.LayoutParams timeParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            timeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            timeParams.leftMargin = (int) rlPlayPro.getContext().getResources().getDimension(R.dimen.voide_tool_middle);
            tvTime.setLayoutParams(timeParams);

            RelativeLayout.LayoutParams seekParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            seekParams.addRule(RelativeLayout.LEFT_OF, R.id.video_tool_tvTime);
            seekParams.addRule(RelativeLayout.CENTER_VERTICAL);
            skTime.setLayoutParams(seekParams);
            if (NavigationH > 0) {
                RelativeLayout.LayoutParams screenParams = (RelativeLayout.LayoutParams) tbtnDualScreen.getLayoutParams();
                screenParams.rightMargin = NavigationH;
                tbtnDualScreen.setLayoutParams(screenParams);
            }
        } else {
            imgFullscreen.setVisibility(View.VISIBLE);
            tbtnGyro.setVisibility(View.GONE);
            tbtnDualScreen.setVisibility(View.GONE);
            RelativeLayout.LayoutParams playParams = (RelativeLayout.LayoutParams) rlPlayPro.getLayoutParams();
            playParams.addRule(RelativeLayout.LEFT_OF, R.id.video_tool_imgFullscreen);
            rlPlayPro.setLayoutParams(playParams);

            RelativeLayout.LayoutParams seekParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            skTime.setLayoutParams(seekParams);

            RelativeLayout.LayoutParams timeParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            timeParams.addRule(RelativeLayout.BELOW, R.id.video_tool_Seekbar);
            timeParams.leftMargin = (int) tvTime.getContext().getResources().getDimension(R.dimen.little_spacing);
            tvTime.setLayoutParams(timeParams);

        }
    }

    /**
     * 设置时间和进度条初始信息
     */
    public void setInfo() {
        int duration = 0;
        if (player != null) {
            duration = (int) player.getDuration();
        }
        if (duration == skTime.getMax()) {
            return;
        }
        // 设置控制条,放在加载完成以后设置，防止获取getDuration()错误
        skTime.setProgress(0);
        skTime.setMax(duration);
        // 设置播放时间
        videoTimeString = Utils.getShowTime(duration);
        tvTime.setText("00:00:00/" + videoTimeString);
    }

    /*******************************************************
     * 通过Handler来更新进度条
     ******************************************************/
    private Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://更新进度条
                    if (player != null) {
                        int position = (int) player.getCurrentPosition();
                        if (position >= 0 && videoTimeString != null) {
                            skTime.setProgress(position);
                            // 设置播放时间
                            String cur = Utils.getShowTime(position);
                            tvTime.setText(cur + "/" + videoTimeString);
                        }
                    }
                    break;
            }

        }

        ;
    };

    private Runnable CacheProRunnable = new Runnable() {
        @Override
        public void run() {
            if (player != null && videoTimeString != null) {
                int lBufferedPos = (int) player.getBufferedPosition();
                skTime.setSecondaryProgress(lBufferedPos);
                if (lBufferedPos < skTime.getMax()) {
                    if (CacheProHandler != null) {
                        CacheProHandler.postDelayed(CacheProRunnable, 1000);
                    }
                } else {
                    stopCachePro();
                }
            }
        }
    };

    public void startCachePro() {
        if (CacheProHandler == null) {
            CacheProHandler = new Handler();
            CacheProHandler.postDelayed(CacheProRunnable, 1000);
        }
    }

    public void stopCachePro() {
        if (CacheProHandler != null) {
            CacheProHandler.removeCallbacks(CacheProRunnable);
            CacheProHandler = null;
        }
    }


    interface PlayerControl {
        void seekTo(long positionMs);

        void play();

        void pause();

        long getDuration();

        long getBufferedPosition();

        long getCurrentPosition();

        void setGyroEnabled(boolean val);

        boolean isGyroEnabled();

        void setDualScreenEnabled(boolean val);

        boolean isDualScreenEnabled();

        void toFullScreen();

        void toolbarTouch(boolean start);
    }
}
