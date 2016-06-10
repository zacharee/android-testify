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

### How Can I Contribute?

We welcome contributions. Follow the steps in the [CONTRIBUTING](CONTRIBUTING.md) file.

### License 

The Mobile Buy SDK is provided under an MIT Licence. See the [LICENSE](LICENSE) file.
