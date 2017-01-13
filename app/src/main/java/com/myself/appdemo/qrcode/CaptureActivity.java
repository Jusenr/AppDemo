package com.myself.appdemo.qrcode;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.myself.appdemo.Constants;
import com.myself.appdemo.MainActivity;
import com.myself.appdemo.R;
import com.myself.appdemo.TotalApplication;
import com.myself.appdemo.YouMengHelper;
import com.myself.appdemo.api.CompanionApi;
import com.myself.appdemo.api.ExploreApi;
import com.myself.appdemo.api.ScanApi;
import com.myself.appdemo.base.PTWDActivity;
import com.myself.appdemo.base.SelectDialog;
import com.myself.appdemo.db.AccountHelper;
import com.myself.appdemo.qrcode.model.bean.GoodsFuncInfoBean;
import com.myself.appdemo.qrcode.model.bean.GoodsListBean;
import com.myself.appdemo.qrcode.model.bean.SerialNumData;
import com.myself.appdemo.qrcode.qrcoder.CameraManager;
import com.myself.appdemo.qrcode.qrcoder.CameraPreview;
import com.myself.appdemo.utils.ScanUrlParseUtils;
import com.myself.mylibrary.http.callback.JSONObjectCallback;
import com.myself.mylibrary.util.DensityUtil;
import com.myself.mylibrary.util.ImageUtils;
import com.myself.mylibrary.util.Logger;
import com.myself.mylibrary.util.NetworkUtils;
import com.myself.mylibrary.util.StringUtils;
import com.myself.mylibrary.util.ToastUtils;
import com.myself.mylibrary.view.bubble.TooltipView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 二维码识别
 * Created by riven_chris on 2015/11/4.
 */
public class CaptureActivity extends PTWDActivity<TotalApplication> implements View.OnClickListener {
    public static final String SCAN_TYPE = "scan_type";//扫描目的
    public static final int SCAN_CHILD = 101;//扫描孩子二维码
    public static final int SCAN_PRODUCT = 102;//扫描产品二维码
    public static final int SCAN_ANY = 100;//扫描任意

    private final int ALBUM_REQCODE = 2;//相册选择

    private CameraPreview mPreview;
    private Camera mCamera;
    private Handler autoFocusHandler;
    private CameraManager mCameraManager;
    @BindView(R.id.capture_preview)
    FrameLayout scanPreview;

    @BindView(R.id.capture_container)
    RelativeLayout scanContainer;
    @BindView(R.id.fl_capture)
    FrameLayout flCapture;
    @BindView(R.id.capture_crop_view)
    ImageView scanCropView;
    @BindView(R.id.scan_line)
    ImageView scan_line;
    @BindView(R.id.tv_question_1)
    TextView tv_question_1;
    @BindView(R.id.ttv_question_1)
    TooltipView ttv_question_1;
    @BindView(R.id.tv_question_2)
    TextView tv_question_2;
    @BindView(R.id.ttv_question_2)
    TooltipView ttv_question_2;

    private SelectDialog mDialog;
    private GoodsListBean.GoodsInfoBean goodsInfo;
    private GoodsFuncInfoBean goodsFuncInfoBean;
    private String mAppid;
    private String mAppName;
    private String mGoodid;
    private String mChildId;
    private int scan_type;
    private boolean to_create_child;
    private boolean to_help_login;
    private String lastScanResult = "";

    private Rect mCropRect = null;
    private String picturePath;
    private boolean previewing = true;
    private boolean isRequesting = false;
    private byte[] mResultByte;
    private Camera mResultCamera;
    private String mResult;
    private int time = 0;

    public static final int REQUEST_BLUETOOTH_GRANT = 10001;

    private Camera.PreviewCallback previewCb;
    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    //2016/8/18  不用ZBarDecoder改成用ZXing库
    private Runnable doResult = new Runnable() {
        public void run() {
            MultiFormatReader multiFormatReader = new MultiFormatReader();
            try {
                Camera.Size size = mCamera.getParameters().getPreviewSize();
                // 处理异常
                if (mResultByte == null || mResultByte.length == -1) return;
                // 这里需要将获取的data翻转一下，因为相机默认拿的的横屏的数据
                byte[] rotatedData = new byte[mResultByte.length / 4];
                for (int y = 0; y < size.height / 2; y++) {
                    for (int x = 0; x < size.width / 2; x++)
                        rotatedData[x * size.height / 2 + size.height / 2 - y - 1] = mResultByte[2 * x + 2 * y * size.width];
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

                initCrop();
                PlanarYUVLuminanceSource source = buildLuminanceSource(rotatedData, size.width, size.height, mCropRect);

                if (source != null) {
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    rawResult = multiFormatReader.decodeWithState(bitmap);
                    mResult = rawResult.getText();
                }

                if (!TextUtils.isEmpty(mResult)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            processor(mResult);//处理结果
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                multiFormatReader.reset();
            }
        }
    };

    //2016/8/18  不用ZBarDecoder改成用ZXing库
    private Runnable doLocalResult = new Runnable() {
        public void run() {
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
            //加载图片时动态获取采样率，防止OOM
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(picturePath, opts);
            opts.inSampleSize = computeSampleSize(opts, -1, 512 * 512);
            opts.inJustDecodeBounds = false;
            try {
                rawResult = multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(
                        new BitmapLuminanceSource(BitmapFactory.decodeFile(picturePath, opts)))));
                mResult = rawResult.getText();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                multiFormatReader.reset();
            }
            if (!TextUtils.isEmpty(mResult)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processor(mResult);//处理结果
                    }
                });
            } else {
                ToastUtils.showToastShort(mContext, "请扫描葡萄产品的二维码！");
            }
        }
    };

    // Mimic continuous auto-focusing
    private Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };
    //扫面线动画
    private TranslateAnimation animation;
    private HandlerThread mHandlerThread;
    private String deviceSerialNum;
    private String serviceId;
    private String deviceType;
    private String serialNum;
    private String deviceMacAddress;

    // TODO: 2017/1/13
    @Override
    protected int getLayoutId() {
        return R.layout.activity_capture;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        YouMengHelper.onEvent(mContext, YouMengHelper.Scan_action, "扫一扫");
        addNavigation();
        setMainTitleColor(Color.WHITE);
        initViews();
        initAnimation();
    }

    private void initViews() {
        goodsInfo = (GoodsListBean.GoodsInfoBean) args.getSerializable(Constants.BundleKey.BUNDLE_ACTIVATION);
        goodsFuncInfoBean = (GoodsFuncInfoBean) args.getSerializable(Constants.BundleKey.BUNDLE_ASSIST_IN_LOGGING_IN);
        if (goodsInfo != null) {
            mAppid = goodsInfo.getAppid();
            mAppName = goodsInfo.getGoods_name();
            mGoodid = goodsInfo.getGoods_id();
        } else if (goodsFuncInfoBean != null) {
            mAppid = goodsFuncInfoBean.getAppid();
            mAppName = goodsFuncInfoBean.getGoods_name();
            mGoodid = goodsFuncInfoBean.getGoods_id();
        }
        scan_type = args.getInt(SCAN_TYPE, SCAN_ANY);
        to_create_child = args.getBoolean(Constants.TypeKey.TYPE_CREATE_CHILD, false);
        to_help_login = args.getBoolean(Constants.TypeKey.TYPE_HELP_LOGIN, false);
        mChildId = args.getString(Constants.ParamKey.PARAM_CID, "");
        switch (scan_type) {
            case SCAN_PRODUCT:
                if (to_help_login) {
                    tv_question_1.setText("什么是协助登录？");
                    ttv_question_1.setText(R.string.question_5);
                }
                break;
            case SCAN_CHILD:
                tv_question_1.setText("什么是孩子二维码？");
                ttv_question_1.setText(R.string.question_4);
                break;
        }

        mHandlerThread = new HandlerThread("calculation");
        mHandlerThread.start();
        Looper looper = mHandlerThread.getLooper();
        final Handler handler = new Handler(looper);
        previewCb = new Camera.PreviewCallback() {
            public void onPreviewFrame(byte[] data, Camera camera) {
                mResultCamera = camera;
                mResultByte = data;
                handler.post(doResult);
            }
        };
        autoFocusHandler = new Handler();
        mCameraManager = new CameraManager(this);
        try {
            mCameraManager.openDriver();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showToastShort(mContext, "相机打开失败,请打开相机");
            finish();
            return;
        }
        mCamera = mCameraManager.getCamera();
        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        scanPreview.addView(mPreview);
    }

    private void initAnimation() {
        int height = DensityUtil.dp2px(mContext, 150);
        ObjectAnimator rotationX = ObjectAnimator
                .ofFloat(scan_line, "translationY", -height, height);
        rotationX.setRepeatCount(-1);
        rotationX.setRepeatMode(ValueAnimator.RESTART);
        rotationX.setDuration(1200);
        rotationX.start();
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }

    @OnClick({R.id.tv_question_1, R.id.tv_question_2, R.id.capture_container})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_question_1:
                ttv_question_1.setVisibility(ttv_question_1.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                ttv_question_2.setVisibility(View.GONE);
                break;
            case R.id.tv_question_2:
                ttv_question_2.setVisibility(ttv_question_2.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                ttv_question_1.setVisibility(View.GONE);
                break;
            case R.id.capture_container:
                ttv_question_1.setVisibility(View.GONE);
                ttv_question_2.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onLeftAction() {
        super.onLeftAction();
        finish();
    }

    @Override
    public void onRightAction() {
        super.onRightAction();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ALBUM_REQCODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPreview == null)
            initViews();
    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraManager.closeDriver();
        scan_line.clearAnimation();
        mHandlerThread = null;
        mResultByte = null;
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_BLUETOOTH_GRANT) {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (!bluetoothAdapter.isEnabled())
                finish();
            else {
                isRequesting = false;
            }
            return;
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ALBUM_REQCODE://相册选择
                    Uri selectedImage = data.getData();
                    picturePath = ImageUtils.getImageAbsolutePath(CaptureActivity.this, selectedImage);
                    Logger.d("picturePath:" + picturePath);
                    handler.post(doLocalResult);
                    break;
            }
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
            scanPreview.removeView(mPreview);
            mPreview = null;
        }
    }

    private void stopPreview() {
        if (mCamera != null) {
            try {
                mCamera.stopPreview();
                previewing = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = mCameraManager.getCameraResolution().y;
        int cameraHeight = mCameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

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
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 处理扫描结果得到scheme
     *
     * @param scanResult 扫描结果
     */
    private void processor(final String scanResult) {
        if (isRequesting) return;
        isRequesting = true;
        Logger.d("scan_result:" + scanResult);
        if (!NetworkUtils.isNetworkReachable(mContext)) {
            ToastUtils.showToastShort(mContext, "网络不给力，稍后重试");
            isRequesting = false;
            return;
        }
        if (!StringUtils.isEmpty(lastScanResult) && lastScanResult.equals(scanResult)) {
            isRequesting = false;
            return;
        }
        String scheme = null;
        //json格式
        try {
            JSONObject jsonObject = JSONObject.parseObject(scanResult);
            if (jsonObject.containsKey("type")) {
                String typeValue = jsonObject.getString("type");
                if (!StringUtils.isEmpty(typeValue) && "pico-account-server".equals(typeValue)) {
                    scheme = typeValue;
                }
            }
            if (StringUtils.isEmpty(scheme)) {
                showErrorInfo(scanResult);
            }
        } catch (Exception e) {
            scheme = null;
        }
        //链接格式
        if (scheme == null) {
            try {
                scheme = ScanUrlParseUtils.getScheme(scanResult);
            } catch (Exception e) {
                e.printStackTrace();
                showErrorInfo(scanResult);
                return;
            }
        }
        dealScanScheme(scheme, scanResult);
    }

    /**
     * 处理扫描结果，根据scheme进行下一步
     *
     * @param scheme     扫描结果得到scheme
     * @param scanResult 扫描结果
     */
    private void dealScanScheme(String scheme, final String scanResult) {
        Logger.d("scheme:" + scheme);
        switch (scheme) {
            case ScanUrlParseUtils.Scheme.PUTAO_LOGIN://扫描登录
                if (scan_type != SCAN_ANY) {
                    showErrorInfo(scanResult);
                    break;
                }
                String url = ScanUrlParseUtils.getRequestUrl(scanResult);
                Logger.d("url:" + url);
                isRequesting = true;
                networkRequest(ScanApi.scanLogin(url), new JSONObjectCallback() {
                    @Override
                    public void onSuccess(String url, JSONObject result) {
                        int error_code = result.getInteger("error_code");
                        if (error_code == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString(WebLoginActivity.URL_LOGIN, url);
                            startActivity(WebLoginActivity.class, bundle);
                            finish();
                        } else {
                            String error = result.getString("error");
                            if (!TextUtils.isEmpty(error))
                                ToastUtils.showToastLong(mContext, error);
                            lastScanResult = scanResult;
                            isRequesting = false;
                        }
                        loading.dismiss();
                    }

                    @Override
                    public void onCacheSuccess(String url, JSONObject result) {

                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        loading.dismiss();
                        Thread thread = new Thread(runnable);
                        thread.start();
                    }

                    @Override
                    public void onFinish(String url, boolean isSuccess, String msg) {
                        super.onFinish(url, isSuccess, msg);
                        isRequesting = false;
                    }
                });
                break;
            case ScanUrlParseUtils.Scheme.PUTAO_DEVICE:// 扫描添加设备
                if (scan_type != SCAN_ANY) {
                    showErrorInfo(scanResult);
                    break;
                }
                final String deviceUrl = ScanUrlParseUtils.getDeviceRequestUrl(scanResult);
                Logger.d("proUrl:" + deviceUrl);
                isRequesting = true;
                networkRequest(ExploreApi.addDevice(deviceUrl), new JSONObjectCallback() {
                    @Override
                    public void onSuccess(String url, JSONObject result) {
                        Logger.d(result.toString());
                        int http_code = result.getInteger("http_code");
                        if (http_code == 200) {
                            ToastUtils.showToastLong(mContext, "请更新游戏");
//                            ToastUtils.showToastLong(mContext, "添加成功");
//                            startActivity(GameDetailListActivity.class);
                        } else if (http_code == 4201)
                            ToastUtils.showToastLong(mContext, "重复绑定");
                        else if (http_code == 4200)
                            ToastUtils.showToastLong(mContext, "二维码已过期");
                        else
                            ToastUtils.showToastLong(mContext, "绑定失败");
                        loading.dismiss();
                        finish();
                    }

                    @Override
                    public void onCacheSuccess(String url, JSONObject result) {
                    }

                    @Override
                    public void onFailure(String url, int statusCode, String msg) {
                        loading.dismiss();
                        ToastUtils.showToastShort(mContext, msg);
                        isRequesting = false;
                        finish();
                    }
                });
                break;
            case ScanUrlParseUtils.Scheme.HTTP:
                String scanType = ScanUrlParseUtils.getSingleParams(scanResult, "type");
                if (!StringUtils.isEmpty(scanType)) {
                    //扫描产品二维码
                    if (scan_type != SCAN_ANY && scan_type != SCAN_PRODUCT) {
                        showErrorInfo(scanResult);
                        break;
                    }
                    if (("chat").equals(scanType)) {//其他产品
                        isRequesting = true;
                        final String chat_id = ScanUrlParseUtils.getSingleParams(scanResult, "chat_id");
                        networkRequest(CompanionApi.qrServerHandler(chat_id), new JSONObjectCallback() {
                            @Override
                            public void onSuccess(String url, JSONObject result) {
                                loading.dismiss();
                                Logger.d("qrServerHandler", result.toString());
                                final String data_qrcode = result.toString();
                                int error_code = result.getInteger("error_code");
                                if (error_code == 0) {
                                    SerialNumData serialNumData = new SerialNumData();
                                    serialNumData.setAccount_name(result.getString("account_name"));
                                    serialNumData.setAvatar(result.getString("avatar"));
                                    serialNumData.setLogin_appid(result.getString("appid"));
                                    serialNumData.setDevice_id(result.getString("device_id"));
                                    serialNumData.setUid(result.getString("uid"));
                                    serialNumData.setWeidu_callback(result.getString("service_url"));
                                    serialNumData.setWeidu_service_id(result.getString("service_id"));
                                    serialNumData.setChat_id(chat_id);
                                    dealSerialNumData(serialNumData, data_qrcode, scanResult);
                                } else if (error_code == 200004) {
                                    ToastUtils.showToastLong(mContext, "二维码已过期");
                                    lastScanResult = scanResult;
                                    handler.removeCallbacks(runnable);
                                    isRequesting = false;
                                } else {
                                    ToastUtils.showToastLong(mContext, "二维码错误");
                                    lastScanResult = scanResult;
                                    handler.removeCallbacks(runnable);
                                    isRequesting = false;
                                }
                            }

                            @Override
                            public void onCacheSuccess(String url, JSONObject result) {
                            }

                            @Override
                            public void onFailure(String url, int statusCode, String msg) {
                                loading.dismiss();
                                if (!StringUtils.isEmpty(msg))
                                    ToastUtils.showToastShort(mContext, msg);
                                else
                                    ToastUtils.showToastShort(mContext, "二维码错误");
                                Thread thread = new Thread(runnable);
                                thread.start();
                                isRequesting = false;
                            }
                        });
                    }
                } else {
                    Map<String, String> map = ScanUrlParseUtils.getParams(scanResult);
                    if (map.containsKey("s") && map.containsKey("code")) {//老的扫描产品
                        isRequesting = true;
                        ToastUtils.showToastShort(mContext, "该二维码无效\n请确定游戏app已更新到最新版本");
                        isRequesting = false;
                    } else if (map.containsKey("uid") && map.containsKey("nick_name")) {//新的扫描孩子二维码
                        if (scan_type != SCAN_ANY && scan_type != SCAN_CHILD) {
                            showErrorInfo(scanResult);
                            break;
                        }
                        isRequesting = true;
                        String childId = ScanUrlParseUtils.getSingleParams(scanResult, "uid");
                        String childName = ScanUrlParseUtils.getSingleParams(scanResult, "nick_name");
                        checkYouselfIsBind(null, childId, childName, AccountHelper.getCurrentUid());
                    } else {
                        showErrorInfo(scanResult);
                    }
                }
                break;
            case ScanUrlParseUtils.Scheme.PICO_ACCOUNT_SERVER://扫描PaiBot和浏览器
                if (scan_type != SCAN_ANY && scan_type != SCAN_PRODUCT) {
                    showErrorInfo(scanResult);
                    break;
                }
                String serial_num = "", type = "";
                //兼容json和链接格式
                try {
                    JSONObject jsonObject = JSON.parseObject(scanResult);
                    if (jsonObject.containsKey("serial_num"))
                        serial_num = jsonObject.getString("serial_num");
                    if (jsonObject.containsKey("type"))
                        type = jsonObject.getString("type");
                } catch (Exception e) {
                    Map<String, String> map = ScanUrlParseUtils.getParams(scanResult);
                    if (map.containsKey("serial_num") && map.containsKey("type")) {
                        serial_num = ScanUrlParseUtils.getSingleParams(scanResult, "serial_num");
                        type = ScanUrlParseUtils.getSingleParams(scanResult, "type");
                    }
                }
                Logger.d("serial_num:" + serial_num);
                Logger.d("type:" + type);
                if (StringUtils.isEmpty(serial_num) || StringUtils.isEmpty(type)) {
                    showErrorInfo(scanResult);
                    return;
                }
                isRequesting = true;
                final String serial_num_temp = serial_num;
                break;
            case ScanUrlParseUtils.Scheme.PT_CHILD://旧的扫描孩子二维码
                if (scan_type != SCAN_ANY && scan_type != SCAN_CHILD) {
                    showErrorInfo(scanResult);
                    break;
                }
                isRequesting = true;
                String childId = ScanUrlParseUtils.getSingleParams(scanResult, "cid");
                String childName = ScanUrlParseUtils.getSingleParams(scanResult, "nick_name");
                checkYouselfIsBind(null, childId, childName, AccountHelper.getCurrentUid());
                break;
            case ScanUrlParseUtils.Scheme.BLUTOOTH:

                break;
            default:
                showErrorInfo(scanResult);
                break;
        }
    }

    private void showErrorInfo(String scanResult) {
        String scaneErrorInfo = "请扫描葡萄产品的二维码！";
        switch (scan_type) {
            case SCAN_ANY:
                scaneErrorInfo = "请扫描葡萄产品的二维码！";
                break;
            case SCAN_CHILD:
                scaneErrorInfo = "找不到孩子，请扫描孩子二维码！";
                break;
            case SCAN_PRODUCT:
                scaneErrorInfo = "找不到产品，请扫描产品二维码！";
                break;
        }
        ToastUtils.showToastLong(mContext, scaneErrorInfo);
        lastScanResult = scanResult;
        isRequesting = false;
    }

    private void showWrongProductInfo(String scanResult) {
        ToastUtils.showToastLong(mContext, "请扫描" + mAppName + "的二维码");
        lastScanResult = scanResult;
        if (loading != null && loading.isShowing())
            loading.dismiss();
        if (handler != null)
            handler.removeCallbacks(runnable);
        isRequesting = false;
    }

    private void dealSerialNumData(SerialNumData serialNumData, String data_qrcode, String scanResult) {
        //是否为所选产品二维码
        if (!serialNumData.getLogin_appid().equals(mAppid)) {
            showWrongProductInfo(scanResult);
        } else {
            if (StringUtils.isEmpty(serialNumData.getUid())) {//未登录
                //是否需要创建孩子
                if (to_create_child) {//创建孩子
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("serialNumData", serialNumData);
                    bundle.putString("data_qrcode", data_qrcode);
                    // TODO: 2017/1/12
//                    startActivity(CreateAndBindActivity.class, bundle);
                    startActivity(MainActivity.class, bundle);
                    finish();
                } else {//协助登录
                    helpLogin(serialNumData, data_qrcode, AccountHelper.getCurrentUid());
                }
            } else {//已登录
                checkYouselfIsBind(serialNumData, serialNumData.getUid(), serialNumData.getAccount_name(), AccountHelper.getCurrentUid());
            }
        }
    }

    /**
     * 校验自己是否绑定该孩子
     */
    private void checkYouselfIsBind(final SerialNumData serialNumData, final String child_uid, final String child_name, final String check_uid) {

    }

    /**
     * 协助登录
     */
    private void helpLogin(final SerialNumData serialNumData, final String data_qrcode, final String parent_uid) {

    }

    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height, Rect rect) {
        if (rect == null) {
            return null;
        }
        // Go ahead and assume it's YUV rather than die.
        PlanarYUVLuminanceSource source = null;

        try {
            source = new PlanarYUVLuminanceSource(data, width / 2, height / 2, rect.left / 2, rect.top / 2,
                    rect.width() / 2, rect.height() / 2, false);
        } catch (Exception e) {
        }

        return source;
    }

    class BitmapLuminanceSource extends LuminanceSource {

        private byte bitmapPixels[];

        protected BitmapLuminanceSource(Bitmap bitmap) {
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

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
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

    /**
     * 扫描二维码后加载过程如超过10秒则停止加载，跳出提示语“网络不给力，稍后重试”2s后消失
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time++;
            Message message = handler.obtainMessage();
            message.arg1 = time;
            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 >= 10) {
                loading.dismiss();
                ToastUtils.showToastShort(mContext, "网络不给力，稍后重试");
                isRequesting = false;
                time = 0;
                handler.removeCallbacks(runnable);
            } else
                handler.postDelayed(runnable, 1000);
        }
    };

}
