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
}
