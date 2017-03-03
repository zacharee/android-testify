package com.shopify.testify;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

// Consider subclassing ActivityTestRule
@SuppressWarnings({"unused", "WeakerAccess"})
public class ScreenshotTestRule extends BaseScreenshotTest<ScreenshotTestRule> implements TestRule {

    private final ActivityTestRule<?> activityTestRule;
    private String testName;

    public ScreenshotTestRule(ActivityTestRule<?> activityTestRule) {
        this.activityTestRule = activityTestRule;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        testName = description.getTestClass().getSimpleName() + "_" + description.getMethodName();
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                base.evaluate();
            }
        };
    }

    public ScreenshotTestRule withLayout(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    @Override
    protected String getTestName() {
        return testName;
    }

    @Override
    protected Context getTestContext() {
        return InstrumentationRegistry.getContext();
    }

    @Override
    protected Activity getActivity() {
        return activityTestRule.getActivity();
    }

    @Override
    protected ScreenshotTestRule getThis() {
        return this;
    }
}