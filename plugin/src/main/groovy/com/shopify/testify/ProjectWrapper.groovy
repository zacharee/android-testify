package com.shopify.testify

import org.gradle.api.Project

class ProjectWrapper {

    static private Project project;

    static Project getProject() {
        return project
    }

    static def setProject(Project project) {
        this.project = project
    }

    static def getExtension() {
        TestifyExtension extension = project.getExtensions().findByType(TestifyExtension.class)
        if (extension == null) {
            throw new Exception("define your shit")
        }
        return extension
    }
}
