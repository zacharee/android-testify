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
        /*
        The Testify sample project requires this to be applicationPackageId
        I _think_ ShopifyUX requires this to e the testPackageId
        Not sure why the sample doesn't use the test package
        Perhaps Testify is using the wrong Context to write files
         */
        return "/data/data/${ProjectWrapper.project.testify.testContextId}/app_images/"
    }

    static def getDestinationImageDirectory() {
        return "${ProjectWrapper.project.testify.baselineSourceDir}/${getDeviceKey()}/"
    }

    static def pullScreenshots() {
        println("Copying files...")

        def src = getDeviceImageDirectory() + "."
        def dst = getDestinationImageDirectory()

        println "src:\t${src}"
        println "dst:\t${dst}"

        [getAdbPath(), "-e", 'pull', src, dst].execute()

        // Wait for all the files to be committed to disk
        sleep(ProjectWrapper.project.testify.pullWaitTime);

        println("Ready")
    }
}
