package com.shopify.testify.internal.hardware

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.pm.PackageManager
import android.opengl.GLSurfaceView
import android.os.Build
import android.os.Build.BOARD
import android.os.Build.BOOTLOADER
import android.os.Build.DISPLAY
import android.os.Build.FINGERPRINT
import android.os.Build.HARDWARE
import android.os.Build.HOST
import android.os.Build.ID
import android.os.Build.MANUFACTURER
import android.os.Build.MODEL
import android.os.Build.TIME
import android.os.Build.USER
import android.os.Build.VERSION.RELEASE
import android.os.Build.VERSION.SDK_INT
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyManager
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import java.lang.Character.isLowerCase
import java.lang.Character.toUpperCase
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class HardwareFingerprint {

    enum class ArchitectureType(private val arch: String) {
        x86("i686"),
        x86_64("x86_64"),
        ARM("armv7"),
        ARM64("aarch64"),
        MIPS64("mips64"),
        MIPS("mips"),
        UNKNOWN("");

        companion object {
            fun match(arch: String): ArchitectureType {
                return values().find {
                    it.arch.contains(arch)
                } ?: UNKNOWN
            }
        }
    }

    class DeviceInfoHelper constructor(val context: Context) {

        fun fingerprint(): String {
            return "$model - $board - $host - $version - $id - $fingerPrint - $display"
        }

        val model = deviceModel

        val imei = context.imei

        val hardware: String? = HARDWARE

        val board: String? = BOARD

        val bootloader: String? = BOOTLOADER

        val user: String? = USER

        val host: String? = HOST

        val version: String? = RELEASE

        val apiLevel = SDK_INT

        val id: String? = ID

        val time = TIME

        val fingerPrint: String? = FINGERPRINT

        val display: String? = DISPLAY

        private val deviceModel
            @SuppressLint("DefaultLocale")
            get() = capitalize(
                if (MODEL.toLowerCase().startsWith(MANUFACTURER.toLowerCase())) {
                    MODEL
                } else {
                    "$MANUFACTURER $MODEL"
                }
            )


        private fun capitalize(str: String) = str.apply {
            if (isNotEmpty()) {
                first().run { if (isLowerCase()) toUpperCase() }
            }
        }

        @Suppress("DEPRECATION")
        private val Context.imei
            @SuppressLint("HardwareIds", "MissingPermission")
            get() = telephonyManager.run {
                if (isReadPhoneStatePermissionGranted()) {
                    if (SDK_INT >= Build.VERSION_CODES.O) {
                        imei
                    } else {
                        deviceId
                    }
                } else DEFAULT_DEVICE_ID
            } ?: DEFAULT_DEVICE_ID

        private fun Context.isReadPhoneStatePermissionGranted() =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED

        private val Context.telephonyManager
            get() = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        companion object {
            const val DEFAULT_DEVICE_ID = ""
        }
    }


    private class StubRenderer : GLSurfaceView.Renderer {

        lateinit var rendererName: String
        lateinit var vendorName: String
        lateinit var glVersion: String
        lateinit var glExtensions: List<String>

        private val syncLatch = CountDownLatch(1)

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            gl?.let {
                rendererName = gl.glGetString(GL10.GL_RENDERER)
                vendorName = gl.glGetString(GL10.GL_VENDOR)
                glVersion = gl.glGetString(GL10.GL_VERSION)
                glExtensions = gl.glGetString(GL10.GL_EXTENSIONS).split(" ")
                syncLatch.countDown()
            }
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {}
        override fun onDrawFrame(gl: GL10) {}

        val fingerprint: String by lazy {
            syncLatch.await(5, TimeUnit.SECONDS)
            "$vendorName - $glVersion - $rendererName"
        }
    }

    fun glEsVersion(context: Context): String? {
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as? ActivityManager
        val configurationInfo = activityManager?.deviceConfigurationInfo
        return configurationInfo?.glEsVersion
    }

    fun gpuFingerprint(viewGroup: ViewGroup): String {
        val renderer = StubRenderer()
        val latch = CountDownLatch(1)
        Handler(Looper.getMainLooper()).post {
            val glSurfaceView = GLSurfaceView(viewGroup.context)
            glSurfaceView.setRenderer(renderer)
            viewGroup.addView(glSurfaceView)
            latch.countDown()
        }
        latch.await(2, TimeUnit.SECONDS)
        return renderer.fingerprint
    }

    fun architecture(): ArchitectureType {
        return ArchitectureType.match(System.getProperty("os.arch") ?: "unknown")
    }

    fun is64Bit(): Boolean {


//        return Build.SUPPORTED_ABIS.contains { it.equalsIgnoreCase("x86_64") || it.equalsIgnoreCase("arm64-v8a") }
        return false
    }
}
