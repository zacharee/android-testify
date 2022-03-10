package com.shopify.testify

import android.content.Context
import androidx.annotation.LayoutRes
import com.shopify.testify.internal.modification.FocusModification
import com.shopify.testify.internal.modification.HideCursorViewModification
import com.shopify.testify.internal.modification.HidePasswordViewModification
import com.shopify.testify.internal.modification.HideScrollbarsViewModification
import com.shopify.testify.internal.modification.HideTextSuggestionsViewModification
import com.shopify.testify.internal.modification.SoftwareRenderViewModification
import java.util.Locale

interface ScreenshotConfigurationInterface {
    @get:LayoutRes var targetLayoutId: Int
    var testMethodName: String
    var testClass: String
    var testSimpleClassName: String
    val hideCursorViewModification: HideCursorViewModification
    val hidePasswordViewModification: HidePasswordViewModification
    val hideScrollbarsViewModification: HideScrollbarsViewModification
    val hideTextSuggestionsViewModification: HideTextSuggestionsViewModification
    val softwareRenderViewModification: SoftwareRenderViewModification
    val focusModification: FocusModification
    val testContext: Context
    var assertSameInvoked: Boolean
    var espressoActions: EspressoActions?
    var exactnessValue: Float?
    var fontScale: Float?
    var hideSoftKeyboard: Boolean
    var isLayoutInspectionModeEnabled: Boolean
    var locale: Locale?
    var screenshotViewProvider: ViewProvider?
    var throwable: Throwable?
    var viewModification: ViewModification?
    var extrasProvider: ExtrasProvider?
}
