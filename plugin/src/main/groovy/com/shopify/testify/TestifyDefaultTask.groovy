package com.shopify.testify

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class TestifyDefaultTask extends DefaultTask {

    @Override
    String getGroup() {
        return "Testify"
    }

    @TaskAction
    def action() {
        println("\n\t-- ${getDescription()} --")
        taskAction()
    }

    abstract def taskAction()
}
