package com.shopify.testify.task.utility

import com.shopify.testify.DeviceUtility
import com.shopify.testify.task.TestifyDefaultTask

class ShowLocaleTask extends UtilityTask {

    @Override
    String getDescription() {
        return "Show the locale currently set on the emulator"
    }

    @Override
    def taskAction() {
        println "Language: ${new DeviceUtility(project).language()}"
        println "Country: ${new DeviceUtility(project).country()}"
    }
}
