package com.shopify.testify.task

import com.shopify.testify.DeviceUtility
import org.gradle.api.Project
import org.gradle.api.tasks.TaskContainer

class ScreenshotTestTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Run the screenshot tests"
    }

    @Override
    def taskAction() {


        def testPackage = project.testify.testPackageId
        def testRunner = project.testify.testRunner

        def command = [new DeviceUtility(project).getAdbPath(), '-e', 'shell', 'am', 'instrument', '-e', 'annotation', 'com.shopify.testify.annotation.ScreenshotInstrumentation', '-w', "${testPackage}/${testRunner}"]

        println  "\n\n*********\n"
        println project.testify.applicationPackageId
        println project.testify.baselineSourceDir
        println project.testify.moduleName
        println project.testify.pullWaitTime
        println project.testify.testContextId
        println project.testify.testPackageId
        println project.testify.testRunner
        println "\n\n*********\n"

//        def log = command.execute().text
//        log.eachLine { line -> println line }

//        if (!RecordModeTask.isRecordMode && (log.contains("FAILURES!!!") || log.contains("INSTRUMENTATION_CODE: 0") || log.contains("Process crashed while executing"))) {
//            throw new Exception("Screenshot tests failed");
//        }
//
//        if (RecordModeTask.isRecordMode) {
//            DeviceUtility.pullScreenshots();
//        }
    }

    static def addDependencies(Project project) {

        TaskContainer tasks = project.tasks
        def task = tasks.findByName("screenshotTest")

        task.dependsOn "hidePasswords"

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
