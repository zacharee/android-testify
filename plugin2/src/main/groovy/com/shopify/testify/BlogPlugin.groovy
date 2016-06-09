package com.shopify.testify

import org.gradle.api.Action
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

        addTask(target, "dan", {
            task:
            {
                def adbExe = target.android.getAdbExe().toString()
                println "${adbExe} devices".execute().text
            }
        }, "Testify", "My first task")

        def action = { action -> setAdbPath(target.android.getAdbExe().toString()) }
        target.tasks.create("timezone", ShowTimezoneTask.class, action)
    }
}