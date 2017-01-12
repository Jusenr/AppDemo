package com.myself.appdemo.qrcode.qrcoder;

import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.myself.mylibrary.util.ToastUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Emitter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Cancellable;
import rx.schedulers.Schedulers;

/**
 * Created by riven_chris on 2016/12/12.
 */
public class CameraQRCoder {

    private static final String TAG = "QRCoder";

    private static long AUTO_FOCUS_DELAY = 1000;
    private static long QR_START_DELAY_DEFAULT = 0;
    private static long QR_START_DELAY_LONG = 2500;

    private FragmentActivity mActivity;
    private ViewGroup previewContainer;
    private View qrCropView;
    private CameraPreview mPreview;

    private CameraManager mCameraManager;
    private Rect mCropRect;
    private CameraQRCallback mOnCameraQRCallback;
    private MyPreviewCallback previewCallback;

    private Subscription subscription;
    private Subscription delaySubscription;
    private Observable<byte[]> observable;
    private Handler autoFocusHandler;
    private boolean previewing = true;
    private boolean initialize = false;//是否是初始化

    public CameraQRCoder(FragmentActivity activity, ViewGroup previewContainer, View qrCropView) {
        activity.getWindow().setFormat(PixelFormat.TRANSLUCENT);
        this.mActivity = activity;
        this.previewContainer = previewContainer;
        this.qrCropView = qrCropView;
        initialize = true;
        init();
    }

    public void setOnQRCallback(CameraQRCallback mOnCameraQRCallback) {
        this.mOnCameraQRCallback = mOnCameraQRCallback;
    }

    private void init() {
        autoFocusHandler = new Handler();
        mCameraManager = new CameraManager(mActivity);
        try {
            mCameraManager.openDriver();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToastShort(mActivity, "相机打开失败,请打开相机");
            mActivity.finish();
            return;
        }
        previewCallback = new MyPreviewCallback();

        observable = Observable.fromEmitter(new Action1<Emitter<byte[]>>() {
            @Override
            public void call(final Emitter<byte[]> emitter) {
                Log.d(TAG, "emitter call");
                previewCallback.setEmitter(emitter);
                emitter.setCancellation(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        mCameraManager.getCamera().setPreviewCallback(null);
                        Log.d(TAG, "emitter cancel");
                    }
                });
                mCameraManager.getCamera().setPreviewCallback(previewCallback);
            }
        }, Emitter.BackpressureMode.NONE)
                .subscribeOn(AndroidSchedulers.mainThread())
                .onBackpressureLatest()
                .observeOn(Schedulers.newThread());
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCameraManager.getCamera().autoFocus(autoFocusCallback);
        }
    };

    // Mimic continuous auto-focusing
    private Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, AUTO_FOCUS_DELAY);
        }
    };

    private class MyPreviewCallback implements Camera.PreviewCallback {

        private Emitter<byte[]> emitter;

        public void setEmitter(Emitter<byte[]> emitter) {
            this.emitter = emitter;
        }

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            Log.d(TAG, "onPreviewFrame");
            emitter.onNext(data);
        }
    }

    private void startPreview() {
        if (mPreview == null) {
            previewContainer.removeAllViews();
            mPreview = new CameraPreview(mActivity, mCameraManager.getCamera(), previewCallback, autoFocusCallback);
            previewContainer.addView(mPreview);
        } else {
            mPreview.startPreView();
        }
    }

    private void stopPreview() {
        if (mPreview != null) {
            mPreview.stopPreView();
        }
    }

    public void start() {
        long delay = QR_START_DELAY_LONG;
        if (initialize) {
            delay = QR_START_DELAY_DEFAULT;
            initialize = false;
        }
        releaseSubscriptions();
        delaySubscription = Observable.timer(delay, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        delaySubscription = null;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        delaySubscription = null;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        startPreview();
                        previewing = true;
                        if (mOnCameraQRCallback != null) {
                            mOnCameraQRCallback.onQRStart();
                        }
                        subscription = observable.subscribe(subscriber());
                        delaySubscription = null;
                    }
                });
    }

    public void stop() {
        previewing = false;
        releaseSubscriptions();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stopPreview();
                if (mOnCameraQRCallback != null) {
                    mOnCameraQRCallback.onQRStop();
                }
            }
        });
    }

    public void release() {
        previewing = false;
        if (mCameraManager != null) {
            mCameraManager.closeDriver();
        }
        if (autoFocusHandler != null) {
            autoFocusHandler.removeCallbacksAndMessages(null);
            autoFocusHandler = null;
        }
        releaseSubscriptions();
        observable = null;
        previewCallback = null;
        autoFocusCallback = null;
        doAutoFocus = null;
    }

    private void releaseSubscriptions() {
        if (delaySubscription != null) {
            delaySubscription.unsubscribe();
            delaySubscription = null;
        }
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    private Subscriber<byte[]> subscriber() {
        return new Subscriber<byte[]>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(byte[] bytes) {
                if (isUnsubscribed())
                    return;
                Log.d(TAG, "doResult");
                final String result = doResult(bytes);
                Log.d(TAG, "result: " + result);
                if (!TextUtils.isEmpty(result)) {
                    if (isUnsubscribed())
                        return;
                    stop();
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mOnCameraQRCallback != null) {
                                mOnCameraQRCallback.onQRSuccess(result);
                            }
                        }
                    });
                }
            }
        };
    }

    private String doResult(byte[] bytes) {
        String result = null;
        MultiFormatReader multiFormatReader = new MultiFormatReader();
        try {
            Camera.Size size = mCameraManager.getCamera().getParameters().getPreviewSize();
            // 处理异常
            if (bytes == null || bytes.length == -1) return null;
            // 这里需要将获取的data翻转一下，因为相机默认拿的的横屏的数据
            byte[] rotatedData = new byte[bytes.length / 4];
            for (int y = 0; y < size.height / 2; y++) {
                for (int x = 0; x < size.width / 2; x++)
                    rotatedData[x * size.height / 2 + size.height / 2 - y - 1] = bytes[2 * x + 2 * y * size.width];
            }
            // 宽高也要调整
            int tmp = size.width;
            size.width = size.height;
            size.height = tmp;

            // 可以解析的编码类型
            List<BarcodeFormat> decodeFormats = new ArrayList<BarcodeFormat>();
            decodeFormats.add(BarcodeFormat.QR_CODE);
            // 解码的参数
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>(2);
            hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
            // 设置继续的字符编码格式为UTF8
            hints.put(DecodeHintType.CHARACTER_SET, "UTF8");
            // 设置解析配置参数
            multiFormatReader.setHints(hints);
            // 开始对图像资源解码
            Result rawResult = null;
            if (mCropRect == null) {
                initCropRect(qrCropView, previewContainer);
            }
            PlanarYUVLuminanceSource source = buildLuminanceSource(rotatedData, size.width, size.height, mCropRect);

            if (source != null) {
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                rawResult = multiFormatReader.decodeWithState(bitmap);
                result = rawResult.getText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            multiFormatReader.reset();
        }

        return result;
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCropRect(View qrCropView, ViewGroup previewContainer) {
        int cameraWidth = mCameraManager.getCameraResolution().y;
        int cameraHeight = mCameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        qrCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = qrCropView.getWidth();
        int cropHeight = qrCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = previewContainer.getWidth();
        int containerHeight = previewContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return mActivity.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height, Rect rect) {
        if (rect == null) {
            return null;
        }
        // Go ahead and assume it's YUV rather than die.
        PlanarYUVLuminanceSource source = null;

        try {
            source = new PlanarYUVLuminanceSource(data, width / 2, height / 2, rect.left / 2, rect.top / 2,
                    rect.width() / 2, rect.height() / 2, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return source;
    }
}
