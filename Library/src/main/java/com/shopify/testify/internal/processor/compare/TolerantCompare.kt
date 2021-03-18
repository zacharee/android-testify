package com.shopify.testify.internal.processor.compare

import android.graphics.Bitmap
import com.shopify.testify.internal.processor.ParallelPixelProcessor
import kotlin.math.absoluteValue

class TolerantCompare(private val tolerance: Int) : BitmapCompare {

    override fun compareBitmaps(baselineBitmap: Bitmap, currentBitmap: Bitmap): Boolean {
        if (baselineBitmap.height != currentBitmap.height) {
            return false
        }

        if (baselineBitmap.width != currentBitmap.width) {
            return false
        }

        return ParallelPixelProcessor
            .create()
            .baseline(baselineBitmap)
            .current(currentBitmap)
            .analyze { baselinePixel, currentPixel ->
                if (baselinePixel == currentPixel) {
                    /* return  */ true
                } else {
                    /* return */ comparePixels(baselinePixel, currentPixel)
                }
            }
    }

    private fun comparePixels(baselinePixel: Int, currentPixel: Int): Boolean {
        val r1 = (currentPixel and 0x00FF0000).shr(16)
        val r2 = (baselinePixel and 0x00FF0000).shr(16)

//        if ((r1 - r2).absoluteValue > tolerance) {
//            return false
//        }

        val g1 = (currentPixel and 0x0000FF00).shr(8)
        val g2 = (baselinePixel and 0x0000FF00).shr(8)

//        if ((g1 - g2).absoluteValue > tolerance) {
//            return false
//        }

        val b1 = (currentPixel and 0x000000FF)
        val b2 = (baselinePixel and 0x000000FF)

//        if ((b1 - b2).absoluteValue > tolerance) {
//            return false
//        }

        val k1 = r1 / 3 + g1 / 3 + b1 / 3
        val k2 = r2 / 3 + g2 / 3 + b2 / 3

        if ((k1 - k2).absoluteValue > tolerance) {
            return false
        }

        return true
    }
}
