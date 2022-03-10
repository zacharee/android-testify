package com.shopify.testify

import android.content.pm.ActivityInfo
import android.graphics.Rect
import androidx.annotation.LayoutRes
import androidx.annotation.VisibleForTesting
import androidx.test.platform.app.InstrumentationRegistry
import com.shopify.testify.internal.helpers.OrientationHelper
import com.shopify.testify.internal.modification.FocusModification
import com.shopify.testify.internal.modification.HideCursorViewModification
import com.shopify.testify.internal.modification.HidePasswordViewModification
import com.shopify.testify.internal.modification.HideScrollbarsViewModification
import com.shopify.testify.internal.modification.HideTextSuggestionsViewModification
import com.shopify.testify.internal.modification.SoftwareRenderViewModification
import com.shopify.testify.report.Reporter
import java.util.HashSet
import java.util.Locale

class ScreenshotConfiguration : ScreenshotConfigurationInterface {

    @LayoutRes
    override var targetLayoutId: Int = ScreenshotRule.NO_ID

    @Suppress("MemberVisibilityCanBePrivate")
    override lateinit var testMethodName: String
    override lateinit var testClass: String
    override lateinit var testSimpleClassName: String
    override val hideCursorViewModification = HideCursorViewModification()
    override val hidePasswordViewModification = HidePasswordViewModification()
    override val hideScrollbarsViewModification = HideScrollbarsViewModification()
    override val hideTextSuggestionsViewModification = HideTextSuggestionsViewModification()
    override val softwareRenderViewModification = SoftwareRenderViewModification()
    override val focusModification = FocusModification()
    override val testContext = InstrumentationRegistry.getInstrumentation().context
    override var assertSameInvoked = false
    override var espressoActions: EspressoActions? = null
    override var exactnessValue: Float? = null
    override var fontScale: Float? = null
    override var hideSoftKeyboard = true
    override var isLayoutInspectionModeEnabled = false
    override var locale: Locale? = null
    override var screenshotViewProvider: ViewProvider? = null
    override var throwable: Throwable? = null
    override var viewModification: ViewModification? = null
    override var extrasProvider: ExtrasProvider? = null
    override var reporter: Reporter? = null
    override var exclusionRectProvider: ExclusionRectProvider? = null
    override val exclusionRects = HashSet<Rect>()
    override var orientationToIgnore: Int = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    override lateinit var outputFileName: String
}
