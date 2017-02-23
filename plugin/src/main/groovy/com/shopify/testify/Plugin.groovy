package com.shopify.testify

import com.shopify.testify.task.*
import org.gradle.api.Project

class Plugin implements org.gradle.api.Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("testify", InputSettingsExtension)

        project.tasks.create("showTimeZone", ShowTimeZoneTask.class)
        project.tasks.create("hidePasswords", HidePasswordsTask.class)
        project.tasks.create("deviceKey", ShowDeviceKeyTask.class)
        project.tasks.create("pullScreenshots", PullScreenshotsTask.class)
        project.tasks.create("recordMode", RecordModeTask.class)
        project.tasks.create("clearScreenshots", ClearScreenshotsTask.class)
        project.tasks.create("showArgs", ShowArgsTask.class)
        project.tasks.create("testifyVersion", VersionTask.class)
        project.tasks.create("screenshotTest", ScreenshotTestTask.class)

        project.afterEvaluate {
            InputSettingsExtension.validate(project)
            ScreenshotTestTask.addDependencies(project)
        }
    }
}