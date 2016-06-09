package com.shopify.testify

import org.gradle.api.Project

class Plugin implements org.gradle.api.Plugin<Project> {

    @Override
    void apply(Project target) {
        DeviceUtility.setAdbPath(target.android.getAdbExe().toString())

        target.tasks.create("showTimeZone", ShowTimeZoneTask.class)
        target.tasks.create("hidePasswords", HidePasswordsTask.class)
    }
}