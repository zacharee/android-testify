package com.shopify.testify;

/**
 * Created by danieljette on 2016-03-03.
 * Copyright © 2016 Shopify. All rights reserved.
 */
public class BootstrapTest extends ScreenshotTestCase<TestHarnessActivity> {

    public BootstrapTest() {
        super(TestHarnessActivity.class, R.id.component_placeholder);
    }

    public void testBootstrap() throws Exception {
        new ScreenshotTest(this, R.layout.test_bootstrap).assertSame();
    }
}