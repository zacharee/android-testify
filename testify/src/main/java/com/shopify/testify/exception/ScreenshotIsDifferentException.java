package com.shopify.testify.exception;

/**
 * Created by danieljette on 15-12-09.
 * Copyright © 2015 Shopify. All rights reserved.
 */
public class ScreenshotIsDifferentException extends Exception {

    // TODO: Update with correct gradle commands
    public ScreenshotIsDifferentException() {
        super("\n\n*  The captured screenshot is different from the baseline screenshot.\n" +
                  "*  Run `./gradlew screenshotPull` to view the differences.\n\n");
    }
}
