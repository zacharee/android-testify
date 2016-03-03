package com.shopify.testify;

/**
 * Created by danieljette on 15-12-09.
 * Copyright © 2015 Shopify. All rights reserved.
 */
public class ScreenshotBaselineNotDefined extends Exception {

    public ScreenshotBaselineNotDefined(String testName) {
        super("\n\n*  A baseline screenshot could not be found for '" + testName + "'.\n" +
                "*  To record a baseline screenshot, run `./gradlew recordMode screenshotTest`\n");
    }
}
