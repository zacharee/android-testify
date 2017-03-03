package com.shopify.testify.task

class RecordBaselineTask extends ScreenshotTestTask {

    @Override
    String getDescription() {
        return "Enable baseline image recording."
    }

    @Override
    def taskAction() {
        RecordModeTask.isRecordMode = true
        super.taskAction()
    }
}
