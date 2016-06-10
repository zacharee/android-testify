package com.shopify.testify;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class DeviceIdentifier {

    private DeviceIdentifier() {
    }

    public static String getDescription(@NonNull Context context) {
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics metrics = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();

        int realWidth = 0;
        int realHeight = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(metrics);
            realWidth = metrics.widthPixels;
            realHeight = metrics.heightPixels;
        } else {
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");

                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } catch (Exception e) {
                Log.e(DeviceIdentifier.class.getSimpleName(), "Could not retrieve real device metrics", e);
            }
        }

        return android.os.Build.VERSION.SDK_INT + "-" + realWidth + "x" + realHeight + "@" + metrics.densityDpi + "dp";
    }
}