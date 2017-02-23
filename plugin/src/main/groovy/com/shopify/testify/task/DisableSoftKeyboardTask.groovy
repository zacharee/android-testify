package com.shopify.testify.task

import com.shopify.testify.DeviceUtility

class DisableSoftKeyboardTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Disables the soft keyboard on the emulator"
    }

    @Override
    def taskAction() {
        def command = [new DeviceUtility(project).getAdbPath(), '-e', 'shell', 'settings', 'put', 'secure', 'show_ime_with_hard_keyboard', '0']
        command.execute()
    }
}
