package com.myself.appdemo.video;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.myself.appdemo.R;

/**
 * Created by riven_chris on 16/6/16.
 */
public class PTVideoPlayerView extends FrameLayout {

    private VideoView videoView;

    public PTVideoPlayerView(Context context) {
        this(context, null);
    }

    public PTVideoPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PTVideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView();
    }

    public void initVideoView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_video_player, null);
        addView(v);

        videoView = (VideoView) findViewById(R.id.video);
    }

    public void setVideoUri(Uri uri) {
        videoView.setVideoURI(uri);
        videoView.start();
    }
}
