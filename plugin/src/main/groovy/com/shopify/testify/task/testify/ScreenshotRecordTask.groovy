package com.shopify.testify.task.testify

import com.shopify.testify.task.internal.RecordModeTask

class ScreenshotRecordTask extends ScreenshotTestTask {

    @Override
    String getDescription() {
        return "Run the screenshot tests and record a new baseline"
    }

    @Override
    def taskAction() {
        RecordModeTask.isRecordMode = true
        super.taskAction()
    }
}
