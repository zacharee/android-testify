package com.shopify.testify

import android.view.ViewGroup
import androidx.test.rule.ActivityTestRule
import com.shopify.testify.internal.hardware.HardwareFingerprint
import com.shopify.testify.sample.R
import com.shopify.testify.sample.test.TestHarnessActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test


class HardwareFingerprintTest {

    @get:Rule
    val rule = ActivityTestRule(TestHarnessActivity::class.java)

    @Test
    fun default() {
        val fingerprint = HardwareFingerprint()
        assertEquals("3.0", fingerprint.glEsVersion(rule.activity))

        val parent = rule.activity.findViewById<ViewGroup>(R.id.harness_root)
        assertEquals(
            "Google (ATI Technologies Inc.) - OpenGL ES-CM 1.1 (4.1 ATI-4.2.15) - Android Emulator OpenGL ES Translator (AMD Radeon RX 580 OpenGL Engine)",
            fingerprint.gpuFingerprint(parent)
        )

        assertEquals(HardwareFingerprint.ArchitectureType.x86, fingerprint.architecture())

        assertFalse(fingerprint.is64Bit())

        assertEquals("Google Android SDK built for x86 - goldfish_x86 - abfarm-east4-036 - 10 - QSR1.200715.002 - google/sdk_gphone_x86/generic_x86:10/QSR1.200715.002/6695061:userdebug/dev-keys - sdk_gphone_x86-userdebug 10 QSR1.200715.002 6695061 dev-keys", HardwareFingerprint.DeviceInfoHelper(rule.activity).fingerprint())
    }
}
