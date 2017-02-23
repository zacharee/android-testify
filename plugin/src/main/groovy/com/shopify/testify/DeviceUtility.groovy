package com.shopify.testify

import org.gradle.api.Project

class DeviceUtility {

    Project project

    DeviceUtility(Project project) {
        this.project = project
    }

    def getAdbPath() {
        return project.android.getAdbExe().toString()
    }

    def getDeviceKey() {
        def versionLine = [getAdbPath(), '-e', 'shell', 'getprop', 'ro.build.version.sdk']
        def version = versionLine.execute().text.trim()

        def densityLine = [getAdbPath(), '-e', 'shell', 'wm', 'density']
        def density = densityLine.execute().text.substring("Physical density: ".length()).trim()

        def sizeLine = [getAdbPath(), '-e', 'shell', 'wm', 'size']
        def size = sizeLine.execute().text.substring("Physical size: ".length()).trim()

        return "${version}-${size}@${density}dp"
    }

    def getDeviceImageDirectory() {
        return "/data/data/${project.testify.testContextId}/app_images/"
    }

    def getDestinationImageDirectory() {
        return "${project.testify.baselineSourceDir}/${getDeviceKey()}/"
    }

    def pullScreenshots() {
        println("Copying files...")

        def src = getDeviceImageDirectory() + "."
        def dst = getDestinationImageDirectory()

        println "src:\t${src}"
        println "dst:\t${dst}"

        [getAdbPath(), "-e", 'pull', src, dst].execute()

        // Wait for all the files to be committed to disk
        sleep(project.testify.pullWaitTime);

        println("Ready")
    }
}
