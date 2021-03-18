package com.shopify.testify.internal.processor

import android.graphics.Bitmap
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.nio.IntBuffer
import java.util.BitSet
import kotlin.math.ceil

class ParallelPixelProcessor private constructor() {

    private var baselineBitmap: Bitmap? = null
    private var currentBitmap: Bitmap? = null

    fun baseline(baselineBitmap: Bitmap): ParallelPixelProcessor {
        this.baselineBitmap = baselineBitmap
        return this
    }

    fun current(currentBitmap: Bitmap): ParallelPixelProcessor {
        this.currentBitmap = currentBitmap
        return this
    }

    private fun prepareBuffers(): ImageBuffers {
        val width = currentBitmap!!.width
        val height = currentBitmap!!.height

        return ImageBuffers(
            width = width,
            height = height,
            baselineBuffer = IntBuffer.allocate(width * height),
            currentBuffer = IntBuffer.allocate(width * height)
        ).apply {
            baselineBitmap!!.copyPixelsToBuffer(baselineBuffer)
            currentBitmap!!.copyPixelsToBuffer(currentBuffer)
            baselineBitmap = null
            currentBitmap = null
        }
    }

    fun analyze(analyzer: (baselinePixel: Int, currentPixel: Int) -> Boolean): Boolean {
        val (width, height, baselineBuffer, currentBuffer) = prepareBuffers()

        val size = width * height
        val chunkSize = size / numberOfCores
        val chunks = ceil(size.toFloat() / chunkSize.toFloat()).toInt()
        val results = BitSet(chunks).apply { set(0, chunks) }

        runBlocking {
            launch(executorDispatcher) {
                (0 until chunks).map { chunk ->
                    async {
                        for (i in (chunk * chunkSize) until ((chunk + 1) * chunkSize)) {
                            if (!analyzer(baselineBuffer[i], currentBuffer[i])) {
                                results.clear(chunk)
                                break
                            }
                        }
                    }
                }.awaitAll()
            }
        }
        return results.cardinality() == chunks
    }


    fun transform(transformer: (baselinePixel: Int, currentPixel: Int) -> Int): TransformResult {
        TODO("Not implemented")
    }

    private data class ImageBuffers(
        val width: Int,
        val height: Int,
        val baselineBuffer: IntBuffer,
        val currentBuffer: IntBuffer
    )

    @Suppress("ArrayInDataClass")
    data class TransformResult(
        val width: Int,
        val height: Int,
        val pixels: IntArray
    )

    companion object {
        fun create(): ParallelPixelProcessor {
            return ParallelPixelProcessor()
        }
    }
}
