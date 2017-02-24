package com.shopify.testify.task

class VerifyPrerequisitesTask extends TestifyDefaultTask {

    @Override
    String getDescription() {
        return "Verify that the required ImageMagick tools are installed"
    }

    @Override
    def taskAction() {
        def cmd = "which compare";
        def result = cmd.execute().text

        if (result.contains("compare")) {
            println "\n\tOK\n"
        } else {
            println "\n\t *** Required ImageMagick tool 'compare' not found ***\n"
            throw new Exception("Required ImageMagick tool 'compare' not found");
        }
    }
}
