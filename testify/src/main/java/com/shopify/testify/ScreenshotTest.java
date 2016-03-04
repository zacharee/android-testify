package com.shopify.testify;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.view.ViewGroup;

import com.shopify.testify.exception.ScreenshotBaselineNotDefinedException;
import com.shopify.testify.exception.ScreenshotIsDifferentException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by danieljette on 15-12-16.
 * Copyright © 2015 Shopify. All rights reserved.
 */
public class ScreenshotTest {

    private static final long INFLATE_TIMEOUT_SECONDS = 5;
    private ViewModification viewModification;
    private EspressoActions espressoActions;
    private ScreenshotTestCase testCase;
    @LayoutRes private int layoutId;
    private boolean hideSoftKeyboard = true;

    public ScreenshotTest(ScreenshotTestCase testCase, @LayoutRes int layoutId) {
        this.testCase = testCase;
        this.layoutId = layoutId;
    }

    private String getTestName() {
        return testCase.getClass().getName() + "_" + testCase.getName();
    }

    private Context getTestContext() {
        return testCase.getInstrumentation().getContext();
    }

    public ScreenshotTest setViewModifications(ViewModification viewModification) {
        this.viewModification = viewModification;
        return this;
    }

    public ScreenshotTest setEspressoActions(EspressoActions espressoActions) {
        this.espressoActions = espressoActions;
        return this;
    }

    public ScreenshotTest setHideSoftKeyboard(boolean hideSoftKeyboard) {
        this.hideSoftKeyboard = hideSoftKeyboard;
        return this;
    }

    public void assertSame() throws Exception {

        assertNotNull("You must specify an instrumentation test case.\n\n * Call ScreenshotAssert.instrumentation(this);\n", testCase);
        assertFalse("You must provide a layoutId.\n\n * Call ScreenshotAssert.layoutId(R.layout.test);\n", layoutId == 0);

        final Activity activity = testCase.getActivity();
        final ViewGroup parentView = (ViewGroup) activity.findViewById(testCase.rootViewId);
        final CountDownLatch latch = new CountDownLatch(1);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.getLayoutInflater().inflate(layoutId, parentView, true);
                if (viewModification != null) {
                    viewModification.modifyView(parentView);
                }
                latch.countDown();
            }
        });

        assertTrue(latch.await(INFLATE_TIMEOUT_SECONDS, TimeUnit.SECONDS));

        if (espressoActions != null) {
            espressoActions.performEspressoActions();
        }

        if (hideSoftKeyboard) {
            Espresso.onView(withId(testCase.rootViewId)).perform(ViewActions.closeSoftKeyboard());
        }

        final String testName = getTestName();

        final ScreenshotUtility screenshotUtility = new ScreenshotUtility();
        Bitmap currentBitmap = screenshotUtility.createBitmapFromActivity(activity, testName);
        assertNotNull("Failed to capture bitmap from activity", currentBitmap);

        Bitmap baselineBitmap = screenshotUtility.loadBaselineBitmapForComparison(getTestContext(), testName);
        if (baselineBitmap == null) {
            throw new ScreenshotBaselineNotDefinedException(testName);
        }

        if (screenshotUtility.compareBitmaps(baselineBitmap, currentBitmap)) {
            // Delete the screenshot from the sdcard if it is identical to the current image
            assertTrue("Could not delete cached bitmap " + testName, screenshotUtility.deleteBitmap(activity, testName));
        } else {
            throw new ScreenshotIsDifferentException();
        }
    }

    public interface ViewModification {

        void modifyView(ViewGroup rootView);
    }

    public interface EspressoActions {

        void performEspressoActions();
    }
}
