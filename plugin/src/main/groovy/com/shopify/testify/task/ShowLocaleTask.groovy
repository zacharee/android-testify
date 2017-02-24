package com.shopify.testify.task

import com.shopify.testify.DeviceUtility

class ShowLocaleTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Print the locale currently set on the emulator"
    }

    @Override
    def taskAction() {
        println "Language: ${new DeviceUtility(project).language()}"
        println "Country: ${new DeviceUtility(project).country()}"
    }
}
