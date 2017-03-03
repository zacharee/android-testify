# android-testify

[![GitHub license](https://img.shields.io/badge/license-MIT-lightgrey.svg)](https://github.com/Shopify/android-testify/blob/master/LICENSE)
[![Build Status](https://circleci.com/gh/Shopify/android-testify/tree/master.svg?style=shield&circle-token=a2199afd9a696583d3c35b18d80eba7a0422560b)](https://circleci.com/gh/Shopify/android-testify/tree/master)
[ ![Library](https://api.bintray.com/packages/shopify/shopify-android/testify/images/download.svg) ](https://bintray.com/shopify/shopify-android/testify/_latestVersion)
[ ![Plugin](https://api.bintray.com/packages/shopify/shopify-android/testify-plugin/images/download.svg)](https://bintray.com/shopify/shopify-android/testify-plugin/_latestVersion)

### Set Up


Add to your build.gradle:

```
apply from: 'screenshot.gradle'

android {
    defaultConfig {
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    androidTestCompile 'com.shopify.testify:testify:0.0.6'
    androidTestCompile "com.android.support:support-annotations:23.3.0"
    androidTestCompile 'com.android.support.test.espresso:espresso-core:2.2.2'
}
```

```
buildscript {
    repositories {
        maven { url 'http://dl.bintray.com/shopify/shopify-android/' }
    }
    dependencies {
        classpath 'com.shopify.testify:plugin:0.0.7'
    }
}

apply plugin: 'com.shopify.testify'

testifySettings {
    applicationPackageId = project.android.defaultConfig.applicationId
    pullWaitTime = 0
    testRunner = project.android.defaultConfig.testInstrumentationRunner
    baselineSourceDir = "./ShopifyUX/src/androidTest/assets/screenshots"
    moduleName = "ShopifyUX"
}
```
### How to run a single test

Take the following test code:

```
package com.shopify.testifysample;

import com.shopify.testify.ScreenshotTest;
import com.shopify.testify.ScreenshotTestCase;

public class BasicTests extends ScreenshotTestCase<TestHarnessActivity> {

    public BasicTests() {
        super(TestHarnessActivity.class, R.id.component_placeholder);
    }

    public void testBootstrap() throws Exception {
        new ScreenshotTest(this, R.layout.test_bootstrap).assertSame();
    }

    public void testMainActivity() throws Exception {
        new ScreenshotTest(this, R.layout.activity_main).assertSame();
    }
}
```

If you wanted to run a single test, you can use the following command:

`./gradlew screenshotTest -PtestClass=com.shopify.testifysample.BasicTests -PtestName=testBootstrap`

Similarly, you can run & record the test in one step using:

`./gradlew recordBaseline -PtestClass=com.shopify.testifysample.BasicTests -PtestName=testBootstrap`

You can also shorten the syntax slightly to:

`./gradlew recordBaseline -PtestClass=com.shopify.testifysample.BasicTests#testBootstrap`


### TODO:

- update for `testify` extensions
- document all the tasks
- show how to build locally
- demonstrate sharding

### How to build?

#### Plugin:

- Visit https://bintray.com/profile/edit
- Set `BINTRAY_USER` to your personal profile name (from "Your Profile")
  - ex. `export BINTRAY_USER="danieljette"`
- Set `BINTRAY_KEY`. It can be found on the left menu, under "API Key"
  - ex. `export BINTRAY_KEY="8e5e06...d418b"`
- `./gradlew :plugin:bintrayUpload`

Note: You might need to bump the `VERSION_NAME` in `./plugin/build.gradle`

### How Can I Contribute?

We welcome contributions. Follow the steps in the [CONTRIBUTING](CONTRIBUTING.md) file.

### License 

The Mobile Buy SDK is provided under an MIT Licence. See the [LICENSE](LICENSE) file.
