package com.shopify.testify.task

import com.shopify.testify.DeviceUtility
import com.shopify.testify.ProjectWrapper

class ScreenshotTestTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Run the screenshot tests"
    }

    @Override
    def taskAction() {
        def testPackage = ProjectWrapper.extension.testPackageId
        def testRunner = ProjectWrapper.extension.testRunner

        def command = [DeviceUtility.getAdbPath(), '-e', 'shell', 'am', 'instrument', '-e', 'annotation', 'com.shopify.testify.annotation.ScreenshotInstrumentation', '-w', "${testPackage}/${testRunner}"]
        def log = command.execute().text
        log.eachLine { line -> println line }

        if (!RecordModeTask.isRecordMode && (log.contains("FAILURES!!!") || log.contains("INSTRUMENTATION_CODE: 0") || log.contains("Process crashed while executing"))) {
            throw new Exception("Screenshot tests failed");
        }

        if (RecordModeTask.isRecordMode) {
            DeviceUtility.pullScreenshots();
        }
    }

    static def addDependencies(ScreenshotTestTask task) {
        task.dependsOn "hidePasswords"
        // TODO: Distinguish between lib and app
//        task.dependsOn ":${ProjectWrapper.extension.moduleName}:installDebug"
//        task.dependsOn ":${ProjectWrapper.extension.moduleName}:installDebugAndroidTest"
        task.dependsOn ":ShopifyUX:installDebugAndroidTest"
    }
}
