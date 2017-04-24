package com.shopify.testify

import com.shopify.testify.task.deprecated.ClearScreenshotsTask
import com.shopify.testify.task.deprecated.PullScreenshotsTask
import com.shopify.testify.task.internal.PullScreenshotsSyncTask
import com.shopify.testify.task.internal.RecordModeTask
import com.shopify.testify.task.internal.VerifyPrerequisitesTask
import com.shopify.testify.task.testify.ScreenshotClearTask
import com.shopify.testify.task.testify.ScreenshotPullTask
import com.shopify.testify.task.testify.ScreenshotRecordTask
import com.shopify.testify.task.testify.ScreenshotTestTask
import com.shopify.testify.task.utility.*
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
        project.tasks.create("recordMode", RecordModeTask.class)
        project.tasks.create("removeDiffImages", RemoveDiffImagesTask.class)
        project.tasks.create("screenshotTest", ScreenshotTestTask.class)
        project.tasks.create("showDeviceKey", ShowDeviceKeyTask.class)
        project.tasks.create("showLocale", ShowLocaleTask.class)
        project.tasks.create("showSettings", ShowSettingsTask.class)
        project.tasks.create("showTestifyVersion", VersionTask.class)
        project.tasks.create("showTimeZone", ShowTimeZoneTask.class)
        project.tasks.create("verifyPrerequisites", VerifyPrerequisitesTask.class)

        // aliases
        project.tasks.create("screenshotRecord", ScreenshotRecordTask)
        project.tasks.create("screenshotPull", ScreenshotPullTask)
        project.tasks.create("screenshotClear", ScreenshotClearTask)

        project.afterEvaluate {
            InputSettingsExtension.validate(project)

            project.tasks.findByName("screenshotTest").addDependencies(project);
            project.tasks.findByName("screenshotRecord").addDependencies(project);

            GenerateDiffImagesTask.addDependencies(project)
        }

        project.dependencies.add("androidTestCompile", "com.shopify.testify:testify:" + getClass().getPackage().getImplementationVersion())
    }
}