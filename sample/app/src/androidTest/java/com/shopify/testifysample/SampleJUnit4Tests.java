package com.shopify.testifysample;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shopify.testify.ScreenshotTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SampleJUnit4Tests {

    @Rule
    public final ActivityTestRule<TestHarnessActivity> activityRule = new ActivityTestRule<>(TestHarnessActivity.class);

    @Test
    public void bootstrap() throws Exception {

        activityRule.getActivity();
//        new ScreenshotTest(activityRule, R.layout.test_bootstrap).assertSame();
    }
}
