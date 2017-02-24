package com.shopify.testify.task

import com.shopify.testify.DeviceUtility
import org.gradle.api.Project
import org.gradle.api.tasks.TaskContainer

class GenerateDiffImagesTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Generate diff images for failed screenshot tests"
    }

    @Override
    def taskAction() {
        new DeviceUtility(project).generateDiffs();
    }

    static def addDependencies(Project project) {
        TaskContainer tasks = project.tasks
        def task = tasks.findByName("generateDiffImages")
        task.dependsOn(["verifyPrerequisites", "pullScreenshotsSync"])
    }
}
