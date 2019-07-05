package com.github.leondevlifelog.onepixel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class App extends Application {
    private static final String TAG = "App";
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

                Log.d(TAG, activity.getClass().getSimpleName() + "-> onActivityCreated: ");
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                Log.d(TAG, activity.getClass().getSimpleName() + "-> onActivityStarted: ");
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                Log.d(TAG, activity.getClass().getSimpleName() + "-> onActivityResumed: ");
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                Log.d(TAG, activity.getClass().getSimpleName() + "-> onActivityPaused: ");
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                Log.d(TAG, activity.getClass().getSimpleName() + "-> onActivityStopped: ");
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                Log.d(TAG, activity.getClass().getSimpleName() + "-> onActivityDestroyed: ");
            }
        });
    }
}
