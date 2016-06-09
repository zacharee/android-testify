package com.shopify.testify

class DeviceKeyTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Display the device key for the current emulator. Format: {api_version}-{width_in_pixels x height_in_pixels}@{dpi}"
    }

    @Override
    def taskAction() {
        def deviceKey = DeviceUtility.getDeviceKey()
        println "\n\t\t=> \"${deviceKey}\""
    }
}
