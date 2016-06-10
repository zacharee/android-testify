package com.shopify.testify

class TestifyExtension {

    private String applicationPackageId;
    private long pullWaitTime;

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
