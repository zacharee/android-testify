package com.shopify.testify.task.testify

import com.shopify.testify.task.internal.PullScreenshotsSyncTask

class ScreenshotPullTask extends PullScreenshotsSyncTask {

    @Override
    boolean isHidden() {
        return false
    }
}
