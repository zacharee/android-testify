package com.shopify.testify;

import android.support.test.annotation.UiThreadTest;
import android.support.test.runner.AndroidJUnit4;

import com.shopify.testify.annotation.ScreenshotInstrumentation;
import com.shopify.testify.exception.AssertSameMustBeLastException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.Stack;

import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class RuleLifecycleTest {

    private static Stack<String> lifecycleVisits = new Stack<>();

    @Rule public ScreenshotTestRule<TestActivity> rule = new ScreenshotTestRule<>(TestActivity.class);
    @Rule public ExpectedException thrown = ExpectedException.none();

    private static void assertExpectedOrder(int order, String tag) {
        if (lifecycleVisits.size() != order) {
            fail(String.format("In method [%s], expected %d but was %d\n%s", tag, order, lifecycleVisits.size(), lifecycleVisits.toString()));
        }
        lifecycleVisits.push(tag);
    }

    @BeforeClass
    public static void beforeClass() {
        assertExpectedOrder(0, "beforeClass");
    }

    @Before
    public void beforeMethod() {
        assertExpectedOrder(1, "beforeMethod");
    }

    @ScreenshotInstrumentation
    @Test
    public void testMethod1() {
        assertExpectedOrder(2, "testMethod1");
        rule.setViewModifications((viewGroup) -> assertExpectedOrder(3, "testMethod1_setViewModifications"));
        rule.assertSame();
    }

    @Test
    public void testMethod2() {
        assertExpectedOrder(2, "testMethod2");
        assertExpectedOrder(3, "testMethod2");

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("java.lang.Exception: Please add @ScreenshotInstrumentation for the test 'testMethod2'");

        rule.assertSame();
    }

    @ScreenshotInstrumentation
    @Test
    public void testMethod3() {
        assertExpectedOrder(2, "testMethod3");
        assertExpectedOrder(3, "testMethod3");

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("\n* You must call assertSame on the ScreenshotTestRule *");
    }

    @UiThreadTest
    @ScreenshotInstrumentation
    @Test
    public void testMethod4() {
        assertExpectedOrder(2, "testMethod4");
        assertExpectedOrder(3, "testMethod4");

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("\n\n*  You can not use Testify on the UI thread.\n*  Remove the @UiThreadTest annotation.\n");

        rule.assertSame();
    }

    @ScreenshotInstrumentation
    @Test
    public void testMethod5() {
        assertExpectedOrder(2, "testMethod5");
        assertExpectedOrder(3, "testMethod5");

        thrown.expect(AssertSameMustBeLastException.class);

        rule.setViewModifications((viewGroup) -> {});
        rule.assertSame();
        rule.setEspressoActions(() -> {});
    }

    @After
    public void afterMethod() {
        assertExpectedOrder(4, "afterMethod");

        lifecycleVisits.pop(); // pop 'afterMethod'

        lifecycleVisits.pop(); // pop method
        lifecycleVisits.pop();

        lifecycleVisits.pop(); // pop 'beforeMethod'
    }

    @AfterClass
    public static void afterClass() {
        assertExpectedOrder(1, "afterClass");
    }
}
