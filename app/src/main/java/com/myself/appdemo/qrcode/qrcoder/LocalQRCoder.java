package com.myself.appdemo.qrcode.qrcoder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by riven_chris on 2016/12/12.
 */
public class LocalQRCoder {

    private LocalQRCallback mOnLocalQRCallback;
    private Subscription subscription;

    public LocalQRCoder() {

    }

    public void setOnQRCallback(LocalQRCallback mOnLocalQRCallback) {
        this.mOnLocalQRCallback = mOnLocalQRCallback;
    }

    public void startWithFile(String filePath) {
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
        subscription = Observable.just(filePath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String filePath) {
                        //加载图片时动态获取采样率，防止OOM
                        BitmapFactory.Options opts = new BitmapFactory.Options();
                        opts.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(filePath, opts);
                        opts.inSampleSize = computeSampleSize(opts, -1, 512 * 512);
                        opts.inJustDecodeBounds = false;
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath, opts);
                        return dealWithBitmap(bitmap);
                    }
                }).subscribe(subscriber());
    }

    public void startWithBitmap(Bitmap bitmap) {
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
        subscription = Observable.just(bitmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Bitmap, String>() {
                    @Override
                    public String call(Bitmap bitmap) {
                        return dealWithBitmap(bitmap);
                    }
                }).subscribe(subscriber());
    }

    private Subscriber<String> subscriber() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String result) {
                if (isUnsubscribed())
                    return;
                if (!TextUtils.isEmpty(result)) {
                    if (mOnLocalQRCallback != null) {
                        mOnLocalQRCallback.onQRStop();
                        mOnLocalQRCallback.onQRSuccess(result);
                    }
                } else {
                    if (mOnLocalQRCallback != null) {
                        mOnLocalQRCallback.onQRStop();
                        mOnLocalQRCallback.onQRFailed("请扫描葡萄产品的二维码！");
                    }
                }
            }
        };
        return subscriber;
    }


    private String dealWithBitmap(Bitmap bitmap) {
        MultiFormatReader multiFormatReader = new MultiFormatReader();
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
        String mResult = null;
        try {
            rawResult = multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(
                    new BitmapLuminanceSource(bitmap))));
            mResult = rawResult.getText();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            multiFormatReader.reset();
        }

        return mResult;
    }

    public void release() {
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    private int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
                Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    private class BitmapLuminanceSource extends LuminanceSource {

        private byte bitmapPixels[];

        BitmapLuminanceSource(Bitmap bitmap) {
            super(bitmap.getWidth(), bitmap.getHeight());

            // 取得该图片的像素数组内容
            int[] data = new int[bitmap.getWidth() * bitmap.getHeight()];
            this.bitmapPixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
            bitmap.getPixels(data, 0, getWidth(), 0, 0, getWidth(), getHeight());

            // 将int数组转换为byte数组，取像素值中蓝色值部分作为辨析内容
            for (int i = 0; i < data.length; i++) {
                this.bitmapPixels[i] = (byte) data[i];
            }
        }

        @Override
        public byte[] getMatrix() {
            // 返回生成好的像素数据
            return bitmapPixels;
        }

        @Override
        public byte[] getRow(int y, byte[] row) {
            // 得到像素数据
            System.arraycopy(bitmapPixels, y * getWidth(), row, 0, getWidth());
            return row;
        }
    }
}
