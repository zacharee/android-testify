package com.shopify.testify

import org.gradle.api.Project

abstract class DeviceUtility {
    static private Project project;

    static def setProject(Project project) {
        this.project = project
    }

    static def getAdbPath() {
        return project.android.getAdbExe().toString()
    }

    static def getDeviceKey() {
        def versionLine = [getAdbPath(), '-e', 'shell', 'getprop', 'ro.build.version.sdk']
        def version = versionLine.execute().text.trim()

        def densityLine = [getAdbPath(), '-e', 'shell', 'wm', 'density']
        def density = densityLine.execute().text.substring("Physical density: ".length()).trim()

        def sizeLine = [getAdbPath(), '-e', 'shell', 'wm', 'size']
        def size = sizeLine.execute().text.substring("Physical size: ".length()).trim()

        return "${version}-${size}@${density}dp"
    }

    static def getExtension() {
        TestifyExtension extension = project.getExtensions().findByType(TestifyExtension.class)
        if (extension == null) {
            throw new Exception("define your shit")
        }
        return extension
    }

    static def getDeviceImageDirectory() {
        return "/data/data/${extension.applicationPackageId}/app_images/";
    }

    static def pullScreenshots() {
        println("Copying files...")

        def src = getDeviceImageDirectory() + "."
        def dst = "./Shopify/src/androidTest/assets/screenshots/" + getDeviceKey() + "/"

        [getAdbPath(), "-e", 'pull', src, dst].execute()

        // Wait for all the files to be committed to disk
        sleep(extension.pullWaitTime);

        println("Ready")
    }
}
