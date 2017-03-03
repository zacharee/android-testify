package com.shopify.testify.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class TestifyDefaultTask extends DefaultTask {

    @Override
    String getGroup() {
        return isHidden() ? "" : "Testify";
    }

    boolean isHidden() {
        return false
    }

    boolean isDeprecated() {
        return false
    }

    @TaskAction
    def action() {
        println("\n\t-- ${getDescription()} --")
        taskAction()
    }

    abstract def taskAction()
}
