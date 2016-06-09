package com.shopify.testify

abstract class DeviceUtility {
    static private def adbPath;

    static def setAdbPath(def adbPath) {
        this.adbPath = adbPath;
    }

    static def getAdbPath() {
        return adbPath
    }

    static def getDeviceKey() {

        def versionLine = new ByteArrayOutputStream()
        exec {
            executable(getAdbPath())
            args(['-e', 'shell', 'getprop', 'ro.build.version.sdk'])
            standardOutput = versionLine
        }
        String version = versionLine.toString().trim();

//        def densityLine = new ByteArrayOutputStream()
//        exec {
//            executable(getAdbPath())
//            args(['-e', 'shell', 'wm', 'density'])
//            standardOutput = densityLine;
//        }
//        String density = densityLine.toString().substring("Physical density: ".length()).trim();
//
//        def sizeLine = new ByteArrayOutputStream()
//        exec {
//            executable(getAdbPath())
//            args(['-e', 'shell', 'wm', 'size'])
//            standardOutput = sizeLine;
//        }
//        String size = sizeLine.toString().substring("Physical size: ".length()).trim();
//
//        return "${version}_${size}@${density}dp";

        return "wtf?"
    }
}
