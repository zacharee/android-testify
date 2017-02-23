package com.shopify.testify.task

import com.shopify.testify.DeviceUtility

class ShowLocaleTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Print the locale currently set on the emulator"
    }

    @Override
    def taskAction() {
        println("Locale")
        def lang = [new DeviceUtility(project).getAdbPath(), '-e', 'shell', 'getprop', 'persist.sys.language']
        def log1 = lang.execute().text
        log1.eachLine { line -> println "Language: ${line}" }
        def country = [new DeviceUtility(project).getAdbPath(), '-e', 'shell', 'getprop', 'persist.sys.country']
        def log2 = country.execute().text
        log2.eachLine { line -> println "Country: ${line}" }
    }
}
