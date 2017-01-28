package com.shopify.testify.task

class VersionTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Displays the Testify plugin version"
    }

    @Override
    def taskAction() {
        println project.version
    }
}
