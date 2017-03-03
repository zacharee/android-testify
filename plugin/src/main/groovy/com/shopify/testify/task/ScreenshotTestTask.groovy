package com.shopify.testify.task

import com.shopify.testify.DeviceUtility
import org.gradle.api.Project
import org.gradle.api.tasks.TaskContainer

class ScreenshotTestTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Run the screenshot tests"
    }

    def getTestTarget() {
        def testClass = project.getProperties().get("testClass")

        def testTarget = ""
        if (testClass != null && testClass != "") {
            testTarget = "-e class " + testClass
            def testName = project.getProperties().get("testName")
            if (testName != null && testName != "") {
                testTarget += "#" + testName
            }
        }

        return testTarget
    }

    static def runAndLog(command) {
        def log
        def process = command.execute()
        process.in.eachLine { line ->
            println line
            log += line
        }
        return log
    }

    private def getShardParams() {
        def shardParams = ""
        def shardIndex = project.getProperties().get("shardIndex")
        def shardCount = project.getProperties().get("shardCount")
        if (shardIndex != null && shardCount != null) {
            shardParams = "-e numShards ${shardCount} -e shardIndex ${shardIndex}"
            println "\nRunning test shard ${shardIndex} of ${shardCount}..."
        }
        return shardParams
    }

    @Override
    def taskAction() {
        def shardParams = getShardParams()
        def testPackage = project.testify.testPackageId
        def testRunner = project.testify.testRunner
        def testTarget = getTestTarget()

        if (RecordModeTask.isRecordMode) {
            new DeviceUtility(project).clearScreenshots()
        }

        def command = [new DeviceUtility(project).getAdbPath(), '-e', 'shell', 'am', 'instrument', shardParams, testTarget, '-e', 'annotation', 'com.shopify.testify.annotation.ScreenshotInstrumentation', '-w', "${testPackage}/${testRunner}"]
        def log = runAndLog(command)

        if (!RecordModeTask.isRecordMode && (log.contains("FAILURES!!!") || log.contains("INSTRUMENTATION_CODE: 0") || log.contains("Process crashed while executing"))) {
            throw new Exception("Screenshot tests failed");
        }

        if (RecordModeTask.isRecordMode) {
            new DeviceUtility(project).pullScreenshots();
        }
    }

    static def addDependencies(Project project) {

        TaskContainer tasks = project.tasks
        def task = tasks.findByName("screenshotTest")

        task.dependsOn "hidePasswords"
        task.dependsOn "disableSoftKeyboard"
        task.dependsOn "showLocale"
        task.dependsOn "showTimeZone"

        def installDebugAndroidTestTask = tasks.findByPath(":${project.testify.moduleName}:installDebugAndroidTest")
        if (installDebugAndroidTestTask != null) {
            task.dependsOn installDebugAndroidTestTask
        }

        def installDebugTask = tasks.findByPath(":${project.testify.moduleName}:installDebug")
        if (installDebugTask != null) {
            task.dependsOn installDebugTask
        }
    }
}
