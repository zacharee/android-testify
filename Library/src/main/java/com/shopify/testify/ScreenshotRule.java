/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Shopify Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.shopify.testify;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.util.Pair;
import android.view.View;

import com.shopify.testify.annotation.BitmapComparisonExactness;
import com.shopify.testify.annotation.ScreenshotInstrumentation;
import com.shopify.testify.annotation.TestifyLayout;
import com.shopify.testify.exception.AssertSameMustBeLastException;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.Locale;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.support.test.InstrumentationRegistry.getInstrumentation;

// Consider subclassing ActivityTestRule
@SuppressWarnings({"unused", "WeakerAccess"})
public class ScreenshotRule<T extends Activity> extends ActivityTestRule<T> implements TestRule {

    @LayoutRes private int targetLayoutId;
    private String testMethodName;
    private String testSimpleClassName;
    private String testClass;
    private Throwable throwable;
    private BaseScreenshotTest.EspressoActions espressoActions;
    private BaseScreenshotTest.ViewModification viewModification;
    private BaseScreenshotTest.ViewProvider viewProvider;
    private Float exactness = null;
    @IdRes private int rootViewId = android.R.id.content;
    private boolean isLayoutInspectionModeEnabled = false;
    public static final String TEST_ACTIVITY_BACKGROUND_COLOR = "background_color";
    public static final String TEST_ACTIVITY_LAYOUT = "activity_layout";
    private boolean assertSameInvoked = false;
    private Intent intent = null;
    private Locale locale = null;
    private boolean hideSoftKeyboard = true;
    private boolean hideScrollbars = true;
    private boolean hidePasswords = true;
    private boolean hideCursor = true;
    private boolean hideTextSuggestions = true;
    private boolean useSoftwareRenderer = false;
    private Instrumentation.ActivityMonitor activityMonitor = null;

    private final Class<T> activityClass;

    public ScreenshotRule(Class<T> activityClass) {
        this(activityClass, false);
    }

    public ScreenshotRule(Class<T> activityClass, boolean initialTouchMode) {
        this(activityClass, initialTouchMode, true);
    }

    public ScreenshotRule(Class<T> activityClass, boolean initialTouchMode, boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);

        this.activityClass = activityClass;
    }

    public ScreenshotRule(Class<T> activityClass, @IdRes int rootViewId) {
        this(activityClass);
        this.rootViewId = rootViewId;
    }

    public String getTestName() {
        return testSimpleClassName + "_" + testMethodName;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public String getTestClassName() {
        return testSimpleClassName;
    }

    public String getTestClass() {
        return testClass;
    }

    @NonNull
    protected Intent getIntent() {
        if (intent == null) {
            intent = new Intent();
        }
        return intent;
    }

    protected void setIntent(@NonNull Intent intent) {
        this.intent = intent;
    }

    @Override
    protected Intent getActivityIntent() {
        Intent intent = super.getActivityIntent();
        if (intent == null) {
            intent = getIntent();
        }
        return intent;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        checkForScreenshotInstrumentationAnnotation(description);

        applyExactness(description);

        espressoActions = null;
        testSimpleClassName = description.getTestClass().getSimpleName();
        testMethodName = description.getMethodName();
        testClass = description.getTestClass().getCanonicalName() + "#" + description.getMethodName();
        final TestifyLayout testifyLayout = description.getAnnotation(TestifyLayout.class);
        targetLayoutId = (testifyLayout != null) ? testifyLayout.layoutId() : View.NO_ID;
        return super.apply(new ScreenshotStatement(base), description);
    }

    private void applyExactness(Description description) {
        if (exactness == null) {
            final BitmapComparisonExactness bitmapComparison = description.getAnnotation(BitmapComparisonExactness.class);
            exactness = (bitmapComparison != null) ? bitmapComparison.exactness() : null;
        }
    }

    public ScreenshotRule setRootViewId(@IdRes int rootViewId) {
        this.rootViewId = rootViewId;
        return this;
    }

    public ScreenshotRule setTargetLayoutId(@LayoutRes int layoutId) {
        this.targetLayoutId = layoutId;
        return this;
    }

    public @LayoutRes
    int getLayoutId() {
        return this.targetLayoutId;
    }

    public ScreenshotRule setEspressoActions(BaseScreenshotTest.EspressoActions espressoActions) {
        if (assertSameInvoked) {
            throw new AssertSameMustBeLastException();
        }
        this.espressoActions = espressoActions;
        return this;
    }

    public ScreenshotRule setViewModifications(BaseScreenshotTest.ViewModification viewModification) {
        if (assertSameInvoked) {
            throw new AssertSameMustBeLastException();
        }
        this.viewModification = viewModification;
        return this;
    }

    public ScreenshotRule setLayoutInspectionModeEnabled(boolean layoutInspectionModeEnabled) {
        this.isLayoutInspectionModeEnabled = layoutInspectionModeEnabled;
        return this;
    }

    public ScreenshotRule setScreenshotViewProvider(BaseScreenshotTest.ViewProvider viewProvider) {
        this.viewProvider = viewProvider;
        return this;
    }

    public ScreenshotRule setLocale(Locale newLocale) {
        this.locale = newLocale;
        return this;
    }

    private void checkForScreenshotInstrumentationAnnotation(Description description) {
        final ScreenshotInstrumentation classAnnotation = description.getTestClass().getAnnotation(ScreenshotInstrumentation.class);
        if (classAnnotation != null) {
            final ScreenshotInstrumentation methodAnnotation = description.getAnnotation(ScreenshotInstrumentation.class);
            if (methodAnnotation != null) {
                this.throwable = new Exception("Please add @ScreenshotInstrumentation for the test \'" + description.getMethodName() + "\'");
            }
        }
    }

    public ScreenshotRule setExactness(@FloatRange(from = 0.0f, to = 1.0f) float exactness) {
        this.exactness = exactness;
        return this;
    }

    public ScreenshotRule setHideSoftKeyboard(boolean hideSoftKeyboard) {
        this.hideSoftKeyboard = hideSoftKeyboard;
        return this;
    }

    public ScreenshotRule setHideScrollbars(boolean hideScrollbars) {
        this.hideScrollbars = hideScrollbars;
        return this;
    }

    public ScreenshotRule setHidePasswords(boolean hidePasswords) {
        this.hidePasswords = hidePasswords;
        return this;
    }

    public ScreenshotRule setHideCursor(boolean hideCursor) {
        this.hideCursor = hideCursor;
        return this;
    }

    public ScreenshotRule setHideTextSuggestions(boolean hideTextSuggestions) {
        this.hideTextSuggestions = hideTextSuggestions;
        return this;
    }

    public ScreenshotRule setUseSoftwareRenderer(boolean useSoftwareRenderer) {
        this.useSoftwareRenderer = useSoftwareRenderer;
        return this;
    }

    @NonNull
    public Instrumentation.ActivityMonitor getActivityMonitor() {
        if (activityMonitor == null) {
            activityMonitor = new Instrumentation.ActivityMonitor(activityClass.getName(), null, true);
        }
        return activityMonitor;
    }

    /**
     * Install an activity monitor and set the requested orientation.
     * Blocks and waits for the orientation change to complete before returning.
     *
     * @param requestedOrientation SCREEN_ORIENTATION_LANDSCAPE or SCREEN_ORIENTATION_PORTRAIT
     */
    public void setOrientation(@IntRange(from = SCREEN_ORIENTATION_LANDSCAPE, to = SCREEN_ORIENTATION_PORTRAIT) int requestedOrientation) {
        if (getActivity().getRequestedOrientation() != requestedOrientation) {
            Instrumentation.ActivityMonitor activityMonitor = getActivityMonitor();
            getInstrumentation().addMonitor(activityMonitor);

            getActivity().setRequestedOrientation(requestedOrientation);
            InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        }
    }

    public void assertSame() {
        assertSameInvoked = true;

        final ScreenshotTest screenshotTest = new ScreenshotTest(targetLayoutId);
        try {
            screenshotTest
                    .setViewModifications(viewModification)
                    .setEspressoActions(espressoActions)
                    .setLayoutInspectionModeEnabled(isLayoutInspectionModeEnabled)
                    .setScreenshotViewProvider(viewProvider)
                    .setScreenshotViewProvider(viewProvider)
                    .setHideSoftKeyboard(hideSoftKeyboard)
                    .setHideScrollbars(hideScrollbars)
                    .setHidePasswords(hidePasswords)
                    .setHideCursor(hideCursor)
                    .setHideTextSuggestions(hideTextSuggestions)
                    .setUseSoftwareRenderer(useSoftwareRenderer);

            if (locale != null) {
                screenshotTest.setLocale(locale);
            }

            if (exactness != null) {
                screenshotTest.assertSame(exactness);
            } else {
                screenshotTest.assertSame();
            }
        } catch (Throwable throwable) {
            this.throwable = throwable;
        } finally {
            removeActivityMonitor();
            /*
            This may look like we're just re-throwing the exception from above
            But, we're actually checking for `throwable`s assigned anywhere within the `ScreenshotTest`
             */
            if (throwable != null) {
                //noinspection ThrowFromFinallyBlock
                throw new RuntimeException(throwable);
            }
        }
    }

    private void removeActivityMonitor() {
        if (activityMonitor != null) {
            getInstrumentation().removeMonitor(activityMonitor);
            activityMonitor = null;
        }
    }

    private class ScreenshotStatement extends Statement {

        private final Statement base;

        private ScreenshotStatement(final Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            assertSameInvoked = false;

            base.evaluate();



//             Safeguard against accidentally omitting the call to `assertSame`
            if (!assertSameInvoked) {
                throw new RuntimeException("\n\n* You must call assertSame on the ScreenshotRule *\n");
            }
        }
    }

    private class ScreenshotTest extends BaseScreenshotTest<ScreenshotTest> {

        ScreenshotTest(@LayoutRes int layoutId) {
            super(layoutId);
        }

        protected Pair<String, String> getTestNameComponents() {
            return new Pair<>(testSimpleClassName, testMethodName);
        }

        @Override
        protected String getFullyQualifiedTestPath() {
            return testClass;
        }

        @Override
        protected Context getTestContext() {
            return InstrumentationRegistry.getContext();
        }

        @Override
        protected Activity getActivity() {
            Activity activity = null;
            if (activityMonitor != null) {
                activity = activityMonitor.getLastActivity();
            }
            if (activity == null) {
                activity = ScreenshotRule.this.getActivity();
            }
            return activity;
        }

        @Override
        protected ScreenshotTest getThis() {
            return this;
        }

        @Override
        protected int getRootViewId() {
            return rootViewId;
        }
    }
}