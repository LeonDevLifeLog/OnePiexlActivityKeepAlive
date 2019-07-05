package com.github.leondevlifelog.onepixel;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class ScreenOffService extends Service {
    private static final String CHANNEL_ID = "com.github.leondevlifelog.onepixel.channel";
    private static final String TAG = "ScreenOffService";
    private final String CHANNEL_NAME = "渠道一";
    private Notification.Builder builder;
    private Notification notification;
    private boolean registered = false;
    private BroadcastReceiver mScreenStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_OFF.equalsIgnoreCase(intent.getAction())) {
                Log.d(TAG, "onReceive: ACTION_SCREEN_OFF");
                Intent intent1 = new Intent(context, OnePixelActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent1.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent1);
            }
        }
    };

    public static void startMe(Context context) {
        Intent intent = new Intent(context, ScreenOffService.class);
        context.startService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            chan.enableLights(true);
            chan.setLightColor(Color.RED);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            builder = new Notification.Builder(this, CHANNEL_ID);
            notification = builder
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("contentText")
                    .setSubText("contentSub")
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentTitle("contentTitle")
                    .build();
        }
        startForeground(1, notification);

        if (!registered) {
            registerReceiver();
            registered = true;
        }
        return START_STICKY;
    }

    private void registerReceiver() {
        Log.d(TAG, "registerReceiver: ");
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mScreenStateReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        unregisterReceiver();
    }

    private void unregisterReceiver() {
        Log.d(TAG, "unregisterReceiver: ");
        unregisterReceiver(mScreenStateReceiver);
    }

}
