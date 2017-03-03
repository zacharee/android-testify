package com.shopify.testify.task.deprecated

import com.shopify.testify.DeviceUtility
import com.shopify.testify.task.TestifyDefaultTask

class ClearScreenshotsTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "[DEPRECATED] Remove any existing screenshot test images from the device"
    }

    @Override
    def taskAction() {
        new DeviceUtility(project).clearScreenshots()
    }
}
