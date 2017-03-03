package com.shopify.testifysample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shopify.testify.ScreenshotTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SampleJUnit4Tests {

    @Rule
    public final ActivityTestRule<TestHarnessActivity> activityTestRule = new ActivityTestRule<>(TestHarnessActivity.class);

    @Rule
    public final ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule(activityTestRule);

    @Test
    public void bootstrap() throws Exception {
        screenshotTestRule.withLayout(R.layout.test_bootstrap).assertSame();
    }
}
