package com.shopify.testify.plugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.junit.Assert.assertTrue

class DemoPluginTest {
    @Test
    public void demo_plugin_should_add_task_to_project() {
        Project project = ProjectBuilder.builder().build()
        project.getPlugins().apply 'com.shopify.testify.plugin.demo.plugin'

        assertTrue(project.tasks.demo instanceof DemoTask)
    }
}