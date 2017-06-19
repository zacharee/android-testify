/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Shopify Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.shopify.testify.task.internal

import com.shopify.testify.DeviceUtility
import com.shopify.testify.task.deprecated.PullScreenshotsTask

class PullScreenshotsSyncTask extends PullScreenshotsTask {

    @Override
    boolean isHidden() {
        return true
    }

    @Override
    String getDescription() {
        return "Pull screenshots and wait for all the files to be committed to disk"
    }

    @Override
    def taskAction() {
        super.taskAction()
        def failedScreenshots = new DeviceUtility(project).detectFailedScreenshots();
        if (failedScreenshots.length > 0) {
            println "Pulling screenshots:"
            for (int i = 0; i < failedScreenshots.size(); i++) {
                println "\tCopied " + new File(failedScreenshots[i]).name + "..."
                sleep(125);
            }
            println "\n"
        }
    }
}
