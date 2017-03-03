package com.shopify.testify.task

import com.shopify.testify.DeviceUtility

class ClearScreenshotsTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Remove any existing screenshot test images from the device"
    }

    @Override
    def taskAction() {
        new DeviceUtility(project).clearScreenshots()
    }
}
