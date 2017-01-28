package com.shopify.testifysample;

import com.shopify.testify.ScreenshotTest;
import com.shopify.testify.ScreenshotTestCase;

/**
 * Created by danieljette on 2016-03-03.
 * Copyright © 2016 Shopify. All rights reserved.
 */
public class BasicTests extends ScreenshotTestCase<TestHarnessActivity> {

    public BasicTests() {
        super(TestHarnessActivity.class, R.id.component_placeholder);
    }

    public void testBootstrap() throws Exception {
        new ScreenshotTest(this, R.layout.test_bootstrap).assertSame();
    }
}