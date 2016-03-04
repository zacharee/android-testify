package com.shopify.testify;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.IdRes;
import android.test.ActivityInstrumentationTestCase2;

import com.shopify.testify.annotation.ScreenshotInstrumentation;


/**
 * Created by carson on 2015-12-04.
 * Copyright © 2015 Shopify. All rights reserved.
 */
@ScreenshotInstrumentation
public class ScreenshotTestCase<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

    @IdRes int rootViewId;

    public ScreenshotTestCase(Class<T> activityClass) {
        super(activityClass);
    }

    public ScreenshotTestCase(Class<T> activityClass, @IdRes int rootViewId) {
        super(activityClass);
        this.rootViewId = rootViewId;
    }

    public void initMockito(Context context) {
        // Required by Mockito
        // Fixes "dexcache == null (and no default could be found; consider setting the 'dexmaker.dexcache' system property)"
        // See https://code.google.com/p/dexmaker/issues/detail?id=2
        System.setProperty("dexmaker.dexcache", context.getCacheDir().getPath());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            throw new Exception("\n * Screenshot testing does not support Android 6+, API 23 (Marshmallow).\n");
//        }

        initMockito(getInstrumentation().getTargetContext());
    }
}
