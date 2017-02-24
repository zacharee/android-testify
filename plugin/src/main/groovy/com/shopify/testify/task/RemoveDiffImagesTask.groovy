package com.shopify.testify.task

import com.shopify.testify.DeviceUtility

class RemoveDiffImagesTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Remove the diff images from local directory"
    }

    @Override
    def taskAction() {
        new DeviceUtility(project).removeFilesWithSubstring(new DeviceUtility(project).getDestinationImageDirectory(), "-diff");
    }
}
