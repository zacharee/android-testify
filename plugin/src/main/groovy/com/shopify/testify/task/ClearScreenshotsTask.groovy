package com.shopify.testify.task

import com.shopify.testify.DeviceUtility

class ClearScreenshotsTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Remove any existing screenshot test images from the device"
    }

    @Override
    def taskAction() {
        def command = [DeviceUtility.getAdbPath(), '-e', 'shell', 'rm', DeviceUtility.getDeviceImageDirectory() + "*.png"]
        def process = command.execute()
        process.in.eachLine { line -> println line }

    }
}
