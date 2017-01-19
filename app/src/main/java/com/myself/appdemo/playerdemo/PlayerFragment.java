package com.myself.appdemo.playerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.myself.appdemo.R;
import com.utovr.player.UVEventListener;
import com.utovr.player.UVInfoListener;
import com.utovr.player.UVMediaPlayer;
import com.utovr.player.UVMediaType;
import com.utovr.player.UVPlayerCallBack;

/**
 * Created by xilin on 2016/8/10.
 */
public class PlayerFragment extends Fragment implements VideoController.PlayerControl {
    private UVMediaPlayer mMediaplayer = null;  // 媒体视频播放器
    private VideoController mCtrl = null;
    private String Path = "http://cache.utovr.com/201508270528174780.m3u8";
    private boolean bufferResume = true;
    private boolean needBufferAnim = true;
    private ImageView imgBuffer;                // 缓冲动画
    private ImageView imgBack;


    public static PlayerFragment newInstance() {
        PlayerFragment f = new PlayerFragment();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player, container, false);
        initView(v);
        //初始化播放器
        RelativeLayout rlPlayView = (RelativeLayout) v.findViewById(R.id.fragment_rlPlayView);
        mMediaplayer = new UVMediaPlayer(getActivity(), rlPlayView, mUVPlayerCallBack);
        //将工具条的显示或隐藏交个SDK管理，也可自己管理
        RelativeLayout rlToolbar = (RelativeLayout) v.findViewById(R.id.fragment_rlToolbar);
        mMediaplayer.setToolbar(rlToolbar, null, imgBack);
        mCtrl = new VideoController(rlToolbar, this, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMediaplayer != null) {
            mMediaplayer.onResume(getActivity());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMediaplayer != null) {
            mMediaplayer.onPause();
        }
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    private void initView(View view) {
        imgBuffer = (ImageView) view.findViewById(R.id.fragment_imgBuffer);
        imgBack = (ImageView) view.findViewById(R.id.fragment_imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releasePlayer();
                getActivity().finish();
            }
        });
    }

    public void releasePlayer() {
        if (mMediaplayer != null) {
            mMediaplayer.release();
            mMediaplayer = null;
        }
    }

    private UVPlayerCallBack mUVPlayerCallBack = new UVPlayerCallBack() {
        @Override
        public void createEnv() {
            try {
                // 创建媒体视频播放器
                mMediaplayer.initPlayer();
                mMediaplayer.setListener(mListener);
                mMediaplayer.setInfoListener(mInfoListener);
                //如果是网络MP4，可调用 mCtrl.startCachePro();mCtrl.stopCachePro();
                mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_M3U8, Path);
                //mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_MP4, "/sdcard/wu.mp4");
            } catch (Exception e) {
                Log.e("utovr", e.getMessage(), e);
            }

        }

        @Override
        public void updateProgress(long position) {
            if (mCtrl != null) {
                mCtrl.updateCurrentPosition();
            }
        }
    };

    private UVEventListener mListener = new UVEventListener() {
        @Override
        public void onStateChanged(int playbackState) {
            Log.i("utovr", "+++++++ playbackState:" + playbackState);
            switch (playbackState) {
                case UVMediaPlayer.STATE_PREPARING:
                    break;
                case UVMediaPlayer.STATE_BUFFERING:
                    if (needBufferAnim && mMediaplayer != null && mMediaplayer.isPlaying()) {
                        bufferResume = true;
                        Utils.setBufferVisibility(imgBuffer, true);
                    }
                    break;
                case UVMediaPlayer.STATE_READY:
                    // 设置时间和进度条
                    mCtrl.setInfo();
                    if (bufferResume) {
                        bufferResume = false;
                        Utils.setBufferVisibility(imgBuffer, false);
                    }
                    break;
                case UVMediaPlayer.STATE_ENDED:
                    //这里是循环播放，可根据需求更改
                    mMediaplayer.replay();
                    break;
                case UVMediaPlayer.TRACK_DISABLED:
                case UVMediaPlayer.TRACK_DEFAULT:
                    break;
            }
        }

        @Override
        public void onError(Exception e, int ErrType) {
            Toast.makeText(getActivity(), Utils.getErrMsg(ErrType), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVideoSizeChanged(int width, int height) {
        }

    };

    private UVInfoListener mInfoListener = new UVInfoListener() {
        @Override
        public void onBandwidthSample(int elapsedMs, long bytes, long bitrateEstimate) {
        }

        @Override
        public void onLoadStarted() {
        }

        @Override
        public void onLoadCompleted() {
            if (bufferResume) {
                bufferResume = false;
                Utils.setBufferVisibility(imgBuffer, false);
            }
            if (mCtrl != null) {
                mCtrl.updateBufferProgress();
            }

        }
    };

    @Override
    public long getDuration() {
        return mMediaplayer != null ? mMediaplayer.getDuration() : 0;
    }

    @Override
    public long getBufferedPosition() {
        return mMediaplayer != null ? mMediaplayer.getBufferedPosition() : 0;
    }

    @Override
    public long getCurrentPosition() {
        return mMediaplayer != null ? mMediaplayer.getCurrentPosition() : 0;
    }

    @Override
    public void setGyroEnabled(boolean val) {
        if (mMediaplayer != null)
            mMediaplayer.setGyroEnabled(val);
    }

    @Override
    public boolean isGyroEnabled() {
        return mMediaplayer != null ? mMediaplayer.isGyroEnabled() : false;
    }

    @Override
    public boolean isDualScreenEnabled() {
        return mMediaplayer != null ? mMediaplayer.isDualScreenEnabled() : false;
    }

    @Override
    public void toolbarTouch(boolean start) {
        if (mMediaplayer != null) {
            if (true) {
                mMediaplayer.cancelHideToolbar();
            } else {
                mMediaplayer.hideToolbarLater();
            }
        }
    }

    @Override
    public void pause() {
        if (mMediaplayer != null && mMediaplayer.isPlaying()) {
            mMediaplayer.pause();
        }
    }

    @Override
    public void seekTo(long positionMs) {
        if (mMediaplayer != null)
            mMediaplayer.seekTo(positionMs);
    }

    @Override
    public void play() {
        if (mMediaplayer != null && !mMediaplayer.isPlaying()) {
            mMediaplayer.play();
        }
    }

    @Override
    public void setDualScreenEnabled(boolean val) {
        if (mMediaplayer != null)
            mMediaplayer.setDualScreenEnabled(val);
    }

    @Override
    public void toFullScreen() {
    }
}
