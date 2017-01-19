package com.myself.appdemo.playerdemo;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import com.myself.appdemo.R;
import com.utovr.player.UVEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by xilin on 2015/12/14.
 */
public class Utils
{
    public static String getShowTime(long milliseconds) {
        // 获取日历函数
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        SimpleDateFormat dateFormat = null;
        // 判断是否大于60分钟，如果大于就显示小时。设置日期格式
        if (milliseconds / 60000 > 60) {
            dateFormat = new SimpleDateFormat("hh:mm:ss");
        } else {
            dateFormat = new SimpleDateFormat("00:mm:ss");
        }
        return dateFormat.format(calendar.getTime());
    }

    public static void startImageAnim(ImageView Img, int anim)
    {
        Img.setVisibility(View.VISIBLE);
        try
        {
            Img.setImageResource(anim);
            AnimationDrawable animationDrawable = (AnimationDrawable) Img.getDrawable();
            animationDrawable.start();
        }
        catch (ClassCastException e)
        {
            e.printStackTrace();
        }
    }

    public static void stopImageAnim(ImageView Img)
    {
        try
        {
            AnimationDrawable animationDrawable = (AnimationDrawable) Img.getDrawable();
            animationDrawable.stop();
        }
        catch (ClassCastException e)
        {
            e.printStackTrace();
        }
        Img.setVisibility(View.GONE);
    }

    public static String getErrMsg(int ErrType)
    {
        String errMsg = null;
        switch (ErrType)
        {
            case UVEventListener.ERR_UNKNOWN:
                errMsg = "unknown";
                break;
            case UVEventListener.ERR_TIMEOUT:
                errMsg = "time out or file is bad";
                break;
            case UVEventListener.ERR_DECODE:
                errMsg = "decode error";
                break;
            case UVEventListener.ERR_RENDER_INIT:
                errMsg = "render set error";
                break;
            case UVEventListener.ERR_INIT:
                errMsg = "init error";
                break;
            case UVEventListener.ERR_WRITE:
                errMsg = "write video or audio track error";
                break;
            case UVEventListener.ERR_LOAD:
                errMsg = "load error or file is bad";
                break;
            case UVEventListener.ERR_TYPE:
                errMsg = "ReaderType, MediaType mismatching";
                break;
            case UVEventListener.ERR_FILE_NOT_EXIST:
                errMsg = "file not exist";
                break;
            case UVEventListener.ERR_ILLEGAL:
                errMsg = "illegal";
                break;
            case UVEventListener.ERR_INVALID:
                errMsg = "invailid";
                break;
            default:
                errMsg = "default";
                break;
        }
            return errMsg;
    }

    //缓冲动画控制
    public static void setBufferVisibility(ImageView imgBuffer, boolean Visible)
    {
        if (Visible)
        {
            imgBuffer.setVisibility(View.VISIBLE);
            Utils.startImageAnim(imgBuffer, R.drawable.play_buffer_anim);
        }
        else
        {
            Utils.stopImageAnim(imgBuffer);
            imgBuffer.setVisibility(View.GONE);
        }
    }

}
