package com.shopify.testify

import org.gradle.api.DefaultTask

class TestifyDefaultTask extends DefaultTask {

    @Override
    String getGroup() {
        return "Testify"
    }
}
