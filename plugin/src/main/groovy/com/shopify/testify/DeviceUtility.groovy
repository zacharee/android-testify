package com.shopify.testify

abstract class DeviceUtility {

    static def getAdbPath() {
        return ProjectWrapper.project.android.getAdbExe().toString()
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

    static def getDeviceImageDirectory() {
        return "/data/data/${ProjectWrapper.extension.applicationPackageId}/app_images/";
    }

    static def pullScreenshots() {
        println("Copying files...")

        def src = getDeviceImageDirectory() + "."
        def dst = "./Shopify/src/androidTest/assets/screenshots/" + getDeviceKey() + "/"

        [getAdbPath(), "-e", 'pull', src, dst].execute()

        // Wait for all the files to be committed to disk
        sleep(ProjectWrapper.extension.pullWaitTime);

        println("Ready")
    }
}
