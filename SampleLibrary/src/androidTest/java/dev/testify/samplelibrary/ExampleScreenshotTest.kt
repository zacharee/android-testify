package dev.testify.samplelibrary

import com.shopify.testify.ScreenshotRule
import com.shopify.testify.annotation.ScreenshotInstrumentation
import org.junit.Rule
import org.junit.Test

class ExampleScreenshotTest {

    @get:Rule val screenshotTestRule = ScreenshotRule(MainActivity::class.java)

    @ScreenshotInstrumentation
    @Test
    fun default() {
        screenshotTestRule.assertSame()
    }
}
