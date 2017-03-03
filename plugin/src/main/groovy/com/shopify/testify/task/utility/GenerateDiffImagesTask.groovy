package com.shopify.testify.task.utility

import com.shopify.testify.DeviceUtility
import org.gradle.api.Project
import org.gradle.api.tasks.TaskContainer

class GenerateDiffImagesTask extends UtilityTask {

    @Override
    String getDescription() {
        return "Create high-contrast images highlighting differences between the current screenshot and the baseline"
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
