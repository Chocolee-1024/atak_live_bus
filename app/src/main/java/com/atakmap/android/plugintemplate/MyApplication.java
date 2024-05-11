package com.atakmap.android.plugintemplate;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    public static final String EVENT_VIEW_TOPIC = "CurrentView";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate!!!!!!!!!!!!!!!!!!");

        // 獲取顯示器度量
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getRealMetrics(displayMetrics);

        // 計算dp, dpi, density
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        Log.d(TAG, "widthPixels: " + displayMetrics.widthPixels + ", heightPixels: " + displayMetrics.heightPixels);
        Log.d(TAG, "dpWidth: " + dpWidth + ", dpHeight: " + dpHeight);
        Log.d(TAG, "density: " + displayMetrics.density);
        Log.d(TAG, "dpi: " + displayMetrics.densityDpi);

    }

}
