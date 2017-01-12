package com.myself.appdemo.qrcode.qrcoder;

/**
 * Created by riven_chris on 2016/12/14.
 */

public interface LocalQRCallback {

    void onQRStart();

    void onQRStop();

    void onQRSuccess(String result);

    void onQRFailed(String msg);
}
