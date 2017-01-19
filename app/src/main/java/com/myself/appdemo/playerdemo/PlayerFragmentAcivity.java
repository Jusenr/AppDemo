package com.myself.appdemo.playerdemo;

import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.myself.appdemo.R;

/**
 * Created by xilin on 2016/8/10.
 */
public class PlayerFragmentAcivity extends FragmentActivity {
    private PowerManager.WakeLock mWakeLock = null;
    private PlayerFragment mPlayerFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_fragment);
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "mytag");
        mWakeLock.acquire();
        mPlayerFragment = PlayerFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragactivity_flPlayer, mPlayerFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWakeLock.release();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPlayerFragment != null) {
                mPlayerFragment.releasePlayer();
                finish();
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

}
