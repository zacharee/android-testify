package com.shopify.testify

import org.gradle.api.Plugin
import org.gradle.api.Project

class BlogPlugin implements Plugin<Project> {

    static void addTask(Project target, def name, task, def group, def description) {

        def newTask = target.tasks.create("$name") << {
            task()
        }

        newTask.group = group
        newTask.description = description
    }

    @Override
    void apply(Project target) {
        def showDevicesTask = target.tasks.create("showDevices") << {
            def adbExe = target.android.getAdbExe().toString()
            println "${adbExe} devices".execute().text
        }
        showDevicesTask.group = "blogplugin"
        showDevicesTask.description = "Runs adb devices command"

        addTask(target, "dan", {
            task:
            {
                def adbExe = target.android.getAdbExe().toString()
                println "${adbExe} devices".execute().text
            }
        }, "Testify", "My first task")

    }
}