package com.shopify.testify.task

import com.shopify.testify.DeviceUtility
import com.shopify.testify.ProjectWrapper
import org.gradle.api.UnknownTaskException
import org.gradle.api.tasks.TaskContainer

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

    static def addDependencies(TaskContainer tasks) {

        def task = tasks.findByName("screenshotTest")

        task.dependsOn "hidePasswords"

        def installDebugAndroidTestTask = tasks.findByPath(":${ProjectWrapper.extension.moduleName}:installDebugAndroidTest")
        if (installDebugAndroidTestTask != null) {
            task.dependsOn installDebugAndroidTestTask
        }

        def installDebugTask = tasks.findByPath(":${ProjectWrapper.extension.moduleName}:installDebug")
        if (installDebugTask != null) {
            task.dependsOn installDebugTask
        }
    }
}
