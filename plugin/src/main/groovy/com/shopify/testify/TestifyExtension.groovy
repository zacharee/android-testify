package com.shopify.testify

class TestifyExtension {

    private String applicationPackageId
    private String testPackageId
    private long pullWaitTime
    private String testRunner
    private String baselineSourceDir
    private String moduleName;
    private String testContextId

    String getTestContextId() {
        if (testContextId == null) {
            testContextId = ProjectWrapper.extension.testPackageId
        }
        return testContextId
    }

    void setTestContextId(String testContextId) {
        this.testContextId = testContextId
    }

    String getModuleName() {
        return moduleName
    }

    void setModuleName(String moduleName) {
        this.moduleName = moduleName
    }

    String getBaselineSourceDir() {
        return baselineSourceDir
    }

    void setBaselineSourceDir(String baselineSourceDir) {
        this.baselineSourceDir = baselineSourceDir
    }

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
        if (ProjectWrapper.project.hasProperty("pullWaitTime")) {
            return ProjectWrapper.project.pullWaitTime.toLong()
        } else {
            return pullWaitTime
        }
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
