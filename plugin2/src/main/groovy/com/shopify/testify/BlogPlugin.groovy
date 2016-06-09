package com.shopify.testify

import org.gradle.api.Plugin
import org.gradle.api.Project

class BlogPlugin implements Plugin<Project> {

    static void addTask(Project target, String name, Closure task, String group, String description) {

        def newTask = target.tasks.create("$name") << {
            task()
        }

        newTask.group = group
        newTask.description = description
    }

    @Override
    void apply(Project target) {

        DeviceUtility.setAdbPath(target.android.getAdbExe().toString())

        addTask(target, "dan", {
            task:
            {
                def adbExe = target.android.getAdbExe().toString()
                println "${adbExe} devices".execute().text
            }
        }, "Testify", "My first task")


        target.tasks.create("showTimeZone", ShowTimeZoneTask.class)
        target.tasks.create("hidePasswords", HidePasswordsTask.class)
    }
}