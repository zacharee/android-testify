package com.shopify.testify

import com.shopify.testify.task.ClearScreenshotsTask
import com.shopify.testify.task.ScreenshotTestTask
import com.shopify.testify.task.ShowDeviceKeyTask
import com.shopify.testify.task.HidePasswordsTask
import com.shopify.testify.task.PullScreenshotsTask
import com.shopify.testify.task.RecordModeTask
import com.shopify.testify.task.ShowTimeZoneTask
import org.gradle.api.Project

class Plugin implements org.gradle.api.Plugin<Project> {

    @Override
    void apply(Project project) {
        ProjectWrapper.setProject(project)

        project.tasks.create("showTimeZone", ShowTimeZoneTask.class)
        project.tasks.create("hidePasswords", HidePasswordsTask.class)
        project.tasks.create("deviceKey", ShowDeviceKeyTask.class)
        project.tasks.create("pullScreenshots", PullScreenshotsTask.class)
        project.tasks.create("recordMode", RecordModeTask.class)
        project.tasks.create("clearScreenshots", ClearScreenshotsTask.class)
        def task = project.tasks.create("screenshotTest", ScreenshotTestTask.class)
        ScreenshotTestTask.addDependencies(task)

        project.extensions.create("testifySettings", TestifyExtension.class)
    }
}