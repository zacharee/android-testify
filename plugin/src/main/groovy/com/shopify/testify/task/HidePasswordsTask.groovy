package com.shopify.testify.task

import com.shopify.testify.DeviceUtility

class HidePasswordsTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Hides the passwords fully on the emulator"
    }

    @Override
    def taskAction() {
        def hidePasswords = [new DeviceUtility(project).getAdbPath(), '-e', 'shell', 'settings', 'put', 'system', 'show_password', '0']
        hidePasswords.execute()
    }
}
