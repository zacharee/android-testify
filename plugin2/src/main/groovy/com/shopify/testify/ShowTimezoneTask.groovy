package com.shopify.testify

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ShowTimezoneTask extends DefaultTask  {

    private String adbPath;

    @Override
    String getName() {
        return "timezone"
    }

    @Override
    String getDescription() {
        return "Shows the emulator timezone"
    }

    public void setAdbPath(String adbPath) {
        this.adbPath = adbPath;
    }

    @TaskAction
    def showTimezone() {
        println("Timezone:")
        println(adbPath)
//        def showTimezone = [getAdbPath(), '-e', 'shell', 'getprop', 'persist.sys.timezone' ]
//        def log = showTimezone.execute().text
//        log.eachLine { line -> println line }
    }
}
