package com.github.leondevlifelog.onepixel;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

public class OnePixelActivity extends Activity {
    private static final int WINDOW_SIZE = BuildConfig.DEBUG ? 40 : 1;
    private static final int WINDOW_COLOR = BuildConfig.DEBUG ? Color.RED : Color.TRANSPARENT;
    private static final String TAG = "OnePixelActivity";
    private boolean mScreenOnRegistered = false;
    private BroadcastReceiver mScreenOnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_ON.equalsIgnoreCase(intent.getAction())) {
                Log.d(TAG, "onReceive: 亮屏");
                ScreenOffService.startMe(context);
                unregisterReceiver();
                finishAndRemoveTask();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pixel);
        Log.d(TAG, "onCreate: ");
        WindowManager.LayoutParams attributes = getWindow().getAttributes();

        attributes.dimAmount = 0.0f;
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = WINDOW_SIZE;
        attributes.height = WINDOW_SIZE;
        attributes.gravity = Gravity.TOP | Gravity.END;
        getWindow().setAttributes(attributes);

        getWindow().getDecorView().setBackgroundColor(WINDOW_COLOR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        registerReceiver();
    }

    private void registerReceiver() {
        if (!mScreenOnRegistered) {
            Log.d(TAG, "registerReceiver: ");
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_ON);
            registerReceiver(mScreenOnReceiver, filter);
            mScreenOnRegistered = true;
        }
    }

    private void unregisterReceiver() {
        if (mScreenOnRegistered) {
            Log.d(TAG, "unregisterReceiver: ");
            unregisterReceiver(mScreenOnReceiver);
            mScreenOnRegistered = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: OpA");
        unregisterReceiver();
    }
}
