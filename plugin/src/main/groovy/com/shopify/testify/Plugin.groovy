package com.shopify.testify

import com.shopify.testify.task.DeviceKeyTask
import com.shopify.testify.task.HidePasswordsTask
import com.shopify.testify.task.PullScreenshotsTask
import com.shopify.testify.task.ShowTimeZoneTask
import org.gradle.api.Project

class Plugin implements org.gradle.api.Plugin<Project> {

    @Override
    void apply(Project project) {
        DeviceUtility.setProject(project)

        project.tasks.create("showTimeZone", ShowTimeZoneTask.class)
        project.tasks.create("hidePasswords", HidePasswordsTask.class)
        project.tasks.create("deviceKey", DeviceKeyTask.class)
        project.tasks.create("pullScreenshots", PullScreenshotsTask.class)

        project.extensions.create("testifySettings", TestifyExtension.class)
    }
}