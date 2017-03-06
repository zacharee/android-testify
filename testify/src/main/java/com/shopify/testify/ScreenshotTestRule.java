package com.shopify.testify;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.shopify.testify.annotation.TestifyLayout;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

// Consider subclassing ActivityTestRule
@SuppressWarnings({"unused", "WeakerAccess"})
public class ScreenshotTestRule<T extends Activity> extends ActivityTestRule<T> implements TestRule {

    private String testName;
    private ScreenshotTest screenshotTest;
    private Throwable throwable;

    public ScreenshotTestRule(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        testName = description.getTestClass().getSimpleName() + "_" + description.getMethodName();
        TestifyLayout testifyLayout = description.getAnnotation(TestifyLayout.class);
        screenshotTest = new ScreenshotTest(testifyLayout.layoutId());

        return new ScreenshotStatement(super.apply(base, description));
    }

    @Override
    protected void beforeActivityLaunched() {
        throwable = null;
    }

    @Override
    protected void afterActivityLaunched() {
        try {
            screenshotTest.assertSame();
        } catch (Throwable throwable) {
            this.throwable = throwable;
        }
    }

    private class ScreenshotStatement extends Statement {

        private final Statement base;

        public ScreenshotStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            try {
                base.evaluate();
            } catch (Throwable ignored) {
            } finally {
                if (throwable != null) {
                    throw throwable;
                }
            }
        }
    }

    private class ScreenshotTest extends BaseScreenshotTest<ScreenshotTest> {

        public ScreenshotTest(@LayoutRes int layoutId) {
            super(layoutId);
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
            return ScreenshotTestRule.this.getActivity();
        }

        @Override
        protected ScreenshotTest getThis() {
            return this;
        }
    }
}