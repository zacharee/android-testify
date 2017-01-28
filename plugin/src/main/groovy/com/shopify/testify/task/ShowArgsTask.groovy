package com.shopify.testify.task

class ShowArgsTask extends TestifyDefaultTask {

    @Override
    def taskAction() {

        if (project.hasProperty("word1")) {
            println project.word1
        }

        if (project.hasProperty("word0")) {
            println project.word0
        }
    }

}
