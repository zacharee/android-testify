package com.shopify.testify.task.testify

import com.shopify.testify.task.deprecated.ClearScreenshotsTask

class ScreenshotClearTask extends ClearScreenshotsTask {

    @Override
    String getDescription() {
        return "Remove any existing screenshot test images from the device"
    }
}
