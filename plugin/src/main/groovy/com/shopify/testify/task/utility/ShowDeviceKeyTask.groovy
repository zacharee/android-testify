package com.shopify.testify.task.utility

import com.shopify.testify.DeviceUtility

class ShowDeviceKeyTask extends UtilityTask {

    @Override
    String getDescription() {
        return "Show the device key for the current emulator. Format: {api_version}-{width_in_pixels}x{height_in_pixels}@{dpi}-{language}"
    }

    @Override
    def taskAction() {
        def deviceKey = new DeviceUtility(project).getDeviceKey()
        println "\n\t${deviceKey}\n"
    }
}
