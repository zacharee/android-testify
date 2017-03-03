package com.shopify.testify

import com.shopify.testify.task.*
import org.gradle.api.Project

class Plugin implements org.gradle.api.Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("testify", InputSettingsExtension)

        project.tasks.create("clearScreenshots", ClearScreenshotsTask.class)
        project.tasks.create("disableSoftKeyboard", DisableSoftKeyboardTask.class)
        project.tasks.create("generateDiffImages", GenerateDiffImagesTask.class)
        project.tasks.create("hidePasswords", HidePasswordsTask.class)
        project.tasks.create("pullScreenshots", PullScreenshotsTask.class)
        project.tasks.create("pullScreenshotsSync", PullScreenshotsSyncTask.class)
        project.tasks.create("recordBaseline", RecordBaselineTask.class)
        project.tasks.create("recordMode", RecordModeTask.class)
        project.tasks.create("removeDiffImages", RemoveDiffImagesTask.class)
        project.tasks.create("screenshotTest", ScreenshotTestTask.class)
        project.tasks.create("showArgs", ShowArgsTask.class)
        project.tasks.create("showDeviceKey", ShowDeviceKeyTask.class)
        project.tasks.create("showLocale", ShowLocaleTask.class)
        project.tasks.create("showSettings", SettingsTask.class)
        project.tasks.create("showTestifyVersion", VersionTask.class)
        project.tasks.create("showTimeZone", ShowTimeZoneTask.class)
        project.tasks.create("verifyPrerequisites", VerifyPrerequisitesTask.class)

        project.afterEvaluate {
            InputSettingsExtension.validate(project)
            ScreenshotTestTask.addDependencies(project)
            GenerateDiffImagesTask.addDependencies(project)
        }

        project.dependencies.add("androidTestCompile", "com.shopify.testify:testify:" + getClass().getPackage().getImplementationVersion())
    }
}