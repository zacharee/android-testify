/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 Shopify Inc.
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
package com.shopify.testify.core

import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Rect
import androidx.annotation.LayoutRes
import androidx.test.platform.app.InstrumentationRegistry
import com.shopify.testify.ScreenshotRule
import com.shopify.testify.internal.modification.FocusModification
import com.shopify.testify.internal.modification.HideCursorViewModification
import com.shopify.testify.internal.modification.HidePasswordViewModification
import com.shopify.testify.internal.modification.HideScrollbarsViewModification
import com.shopify.testify.internal.modification.HideTextSuggestionsViewModification
import com.shopify.testify.internal.modification.SoftwareRenderViewModification
import com.shopify.testify.report.Reporter
import java.util.Locale

class ScreenshotConfiguration : ScreenshotConfigurationInterface {

    @LayoutRes override var targetLayoutId: Int = ScreenshotRule.NO_ID

    override lateinit var testMethodName: String
    override lateinit var testClass: String
    override lateinit var testSimpleClassName: String
    override lateinit var outputFileName: String

    override val hideCursorViewModification = HideCursorViewModification()
    override val hidePasswordViewModification = HidePasswordViewModification()
    override val hideScrollbarsViewModification = HideScrollbarsViewModification()
    override val hideTextSuggestionsViewModification = HideTextSuggestionsViewModification()
    override val softwareRenderViewModification = SoftwareRenderViewModification()
    override val focusModification = FocusModification()
    override var viewModification: ViewModification? = null

    override var assertSameInvoked = false
    override var espressoActions: EspressoActions? = null
    override var exactnessValue: Float? = null
    override val exclusionRects = HashSet<Rect>()
    override var exclusionRectProvider: ExclusionRectProvider? = null
    override var extrasProvider: ExtrasProvider? = null
    override var fontScale: Float? = null
    override var hideSoftKeyboard = true
    override var isLayoutInspectionModeEnabled = false
    override var locale: Locale? = null
    override var orientationToIgnore: Int = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    override var reporter: Reporter? = null
    override var screenshotViewProvider: ViewProvider? = null
    override val testContext: Context = InstrumentationRegistry.getInstrumentation().context
    override var throwable: Throwable? = null
}
