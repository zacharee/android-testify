package com.shopify.testify

import org.gradle.api.tasks.TaskAction

class HidePasswordsTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Hides the passwords fully on the emulator"
    }

    @TaskAction
    def showTimeZone() {
        println("Hide passwords:")
        def hidePasswords = [DeviceUtility.getAdbPath(), '-e', 'shell', 'settings', 'put', 'system', 'show_password', '0']
        hidePasswords.execute()
    }
}
