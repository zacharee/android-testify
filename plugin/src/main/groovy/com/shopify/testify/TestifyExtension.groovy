package com.shopify.testify

class TestifyExtension {

    private String applicationPackageId
    private String testPackageId
    private long pullWaitTime
    private String testRunner

    String getTestPackageId() {
        if (testPackageId == null) {
            return "${applicationPackageId}.test"
        }
        return testPackageId
    }

    void setTestPackageId(String testPackageId) {
        this.testPackageId = testPackageId
    }

    String getTestRunner() {
        return testRunner
    }

    void setTestRunner(String testRunner) {
        this.testRunner = testRunner
    }

    long getPullWaitTime() {
        return pullWaitTime
    }

    void setPullWaitTime(long pullWaitTime) {
        this.pullWaitTime = pullWaitTime
    }

    String getApplicationPackageId() {
        return applicationPackageId
    }

    void setApplicationPackageId(String applicationPackageId) {
        this.applicationPackageId = applicationPackageId
    }
}
