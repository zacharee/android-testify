package com.shopify.testifysample;

import android.support.test.runner.AndroidJUnit4;

import com.shopify.testify.ScreenshotTestRule;
import com.shopify.testify.annotation.TestifyLayout;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SampleJUnit4Tests {

    @Rule
    public final ScreenshotTestRule<TestHarnessActivity> screenshotTestRule = new ScreenshotTestRule<>(TestHarnessActivity.class);

    @Test
    @TestifyLayout(layoutId = R.layout.test_bootstrap)
    public void bootstrap() throws Exception {
    }
}
