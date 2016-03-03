package com.shopify.testify.exception;

/**
 * Created by danieljette on 15-12-09.
 * Copyright © 2015 Shopify. All rights reserved.
 */
public class ScreenshotIsDifferentException extends Exception {

    public ScreenshotIsDifferentException() {
        super("\n\n*  The captured screenshot is different from the baseline screenshot.\n" +
                  "*  Run `./gradlew pullScreenshots` to view the differences.\n\n");
    }
}
