package com.myself.appdemo.qrcode;

import android.os.Bundle;
import android.widget.TextView;

import com.myself.appdemo.R;
import com.myself.appdemo.base.PTWDActivity;
import com.myself.mylibrary.util.StringUtils;

import butterknife.BindView;

public class ScanResultsActivity extends PTWDActivity {

    @BindView(R.id.tv_scan_result)
    TextView mTvScanResult;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_results;
    }

    @Override
    protected void onViewCreatedFinish(Bundle saveInstanceState) {
        addNavigation();


        String result = args.getString("scanResult");
        if (!StringUtils.isEmpty(result))
            mTvScanResult.setText(result);
    }

    @Override
    protected String[] getRequestUrls() {
        return new String[0];
    }
}
