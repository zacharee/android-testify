package com.shopify.testify;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.IdRes;
import android.test.ActivityInstrumentationTestCase2;


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

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            throw new Exception("\n * Screenshot testing does not support Android 6+, API 23 (Marshmallow).\n");
        }

        MockHelper.initMockito(getInstrumentation().getTargetContext());
    }
}
