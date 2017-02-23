package com.shopify.testify.task

class SettingsTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Show current testify extension settings"
    }

    @Override
    def taskAction() {
        println "\tmoduleName:\t\t${project.testify.moduleName}"
        println "\tapplicationPackageId:\t${project.testify.applicationPackageId}"
        println "\ttestContextId:\t\t${project.testify.testContextId}"
        println "\ttestPackageId:\t\t${project.testify.testPackageId}"
        println "\tbaselineSourceDir:\t${project.testify.baselineSourceDir}"
        println "\ttestRunner:\t\t${project.testify.testRunner}"
        println "\tpullWaitTime:\t\t${project.testify.pullWaitTime}"
    }
}
