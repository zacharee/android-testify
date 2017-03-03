package com.shopify.testify.task.internal

import com.shopify.testify.task.TestifyDefaultTask

class RecordModeTask extends TestifyDefaultTask {

    static boolean getIsRecordMode() {
        return isRecordMode
    }

    static boolean isRecordMode = false;

    @Override
    boolean isHidden() {
        return true
    }

    @Override
    String getDescription() {
        return "[DEPRECATED] Enable baseline image recording."
    }

    @Override
    def taskAction() {
        isRecordMode = true
    }
}
