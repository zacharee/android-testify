package com.shopify.testify.task

import com.shopify.testify.DeviceUtility

class PullScreenshotsTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Download the screenshot test images from the device"
    }

    @Override
    def taskAction() {
        new DeviceUtility(project).pullScreenshots()
    }
}
