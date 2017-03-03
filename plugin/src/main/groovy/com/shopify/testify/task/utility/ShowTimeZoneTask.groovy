package com.shopify.testify.task.utility

import com.shopify.testify.DeviceUtility

class ShowTimeZoneTask extends UtilityTask {

    @Override
    String getDescription() {
        return "Show the time zone on the emulator"
    }

    @Override
    def taskAction() {
        def showTimeZone = [new DeviceUtility(project).getAdbPath(), '-e', 'shell', 'getprop', 'persist.sys.timezone']

        println "\n\t\t=> \"${showTimeZone.execute().text.trim()}\""
    }
}
