package com.shopify.testify;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DeviceIdentifier {

    public static String getDescription(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return android.os.Build.VERSION.SDK_INT + "-" + metrics.widthPixels + "x" + metrics.heightPixels + "@" + metrics.densityDpi + "dp";
    }
}