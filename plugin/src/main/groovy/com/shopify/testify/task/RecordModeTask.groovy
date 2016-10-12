package com.shopify.testify.task

class RecordModeTask extends TestifyDefaultTask {

    static boolean getIsRecordMode() {
        return isRecordMode
    }

    static boolean isRecordMode = false;

    @Override
    String getDescription() {
        return "Enable baseline image recording."
    }

    @Override
    def taskAction() {
        isRecordMode = true
    }
}
