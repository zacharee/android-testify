package com.shopify.testify.exception;

/**
 * Created by danieljette on 15-12-09.
 * Copyright © 2015 Shopify. All rights reserved.
 */
public class ScreenshotBaselineNotDefinedException extends Exception {

    public ScreenshotBaselineNotDefinedException(String testName) {
        super("\n\n*  A baseline screenshot could not be found for '" + testName + "'.\n" +
                "*  To record a baseline screenshot, run `./gradlew recordMode screenshotTest`\n");
    }
}
