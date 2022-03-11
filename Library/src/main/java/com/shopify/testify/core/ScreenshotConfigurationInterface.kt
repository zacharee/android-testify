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
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.shopify.testify.internal.modification.FocusModification
import com.shopify.testify.internal.modification.HideCursorViewModification
import com.shopify.testify.internal.modification.HidePasswordViewModification
import com.shopify.testify.internal.modification.HideScrollbarsViewModification
import com.shopify.testify.internal.modification.HideTextSuggestionsViewModification
import com.shopify.testify.internal.modification.SoftwareRenderViewModification
import com.shopify.testify.report.Reporter
import java.util.Locale

typealias ViewModification = (rootView: ViewGroup) -> Unit
typealias EspressoActions = () -> Unit
typealias ViewProvider = (rootView: ViewGroup) -> View
typealias ExtrasProvider = (bundle: Bundle) -> Unit
typealias ExclusionRectProvider = (rootView: ViewGroup, exclusionRects: MutableSet<Rect>) -> Unit

/**
 * Defines various properties that can be modified to customize Testify
 */
interface ScreenshotConfigurationInterface {

    @get:LayoutRes var targetLayoutId: Int

    var testMethodName: String
    var testClass: String
    var testSimpleClassName: String
    var outputFileName: String

    val hideCursorViewModification: HideCursorViewModification
    val hidePasswordViewModification: HidePasswordViewModification
    val hideScrollbarsViewModification: HideScrollbarsViewModification
    val hideTextSuggestionsViewModification: HideTextSuggestionsViewModification
    val softwareRenderViewModification: SoftwareRenderViewModification
    val focusModification: FocusModification
    var viewModification: ViewModification?

    var assertSameInvoked: Boolean
    var espressoActions: EspressoActions?
    var exactnessValue: Float?
    val exclusionRects: HashSet<Rect>
    var exclusionRectProvider: ExclusionRectProvider?
    var extrasProvider: ExtrasProvider?
    var fontScale: Float?
    var hideSoftKeyboard: Boolean
    var isLayoutInspectionModeEnabled: Boolean
    var locale: Locale?
    var orientationToIgnore: Int
    var reporter: Reporter?
    var screenshotViewProvider: ViewProvider?
    val testContext: Context
    var throwable: Throwable?
}
