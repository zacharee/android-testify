package com.shopify.testify

abstract class DeviceUtility {
    static private String adbPath;

    static void setAdbPath(String adbPath) {
        this.adbPath = adbPath;
    }

    static String getAdbPath() {
        return adbPath
    }
}
