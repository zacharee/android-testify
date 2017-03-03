package com.shopify.testify.task.deprecated

import com.shopify.testify.DeviceUtility
import com.shopify.testify.task.TestifyDefaultTask

class PullScreenshotsTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "[DEPRECATED] Download the screenshot test images from the device"
    }

    @Override
    def taskAction() {
        new DeviceUtility(project).pullScreenshots()
    }
}
