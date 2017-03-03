package com.shopify.testify.task.utility

class VersionTask extends UtilityTask {

    @Override
    String getDescription() {
        return "Show the Testify plugin version"
    }

    @Override
    def taskAction() {
        println "${getClass().getPackage().getImplementationVendor()} ${getClass().getPackage().getImplementationTitle()} ${getClass().getPackage().getImplementationVersion()}"
    }
}
