# android-testify

[![Build Status](https://circleci.com/gh/Shopify/android-testify/tree/master.svg?style=shield&circle-token=a2199afd9a696583d3c35b18d80eba7a0422560b)](https://circleci.com/gh/Shopify/android-testify/tree/master)

Requires you add TestHarnessActivity to your manifest

`<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />`

apply from: 'screenshot.gradle'


Add to your build.gradle:

```
    compile project(':testify')
    androidTestCompile project(':testify')
    androidTestCompile "com.android.support:support-annotations:23.1.1"
    androidTestCompile 'com.android.support.test.espresso:espresso-core:2.2.1'
```

```
    defaultConfig {
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
```


```
repositories {
    maven {
        url "http://dl.bintray.com/shopify/shopify-android/"
    }
}

androidTestCompile 'com.shopify.testify:testify:0.0.2'
```
