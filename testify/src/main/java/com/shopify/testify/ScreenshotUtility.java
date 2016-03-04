package com.shopify.testify;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.shopify.testify.exception.ScreenshotDirectoryNotFoundException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by danieljette on 15-12-04.
 * Copyright © 2015 Shopify. All rights reserved.
 */
public class ScreenshotUtility {

    private static final String LOG_TAG = ScreenshotUtility.class.getSimpleName();
    private static final String PNG_EXTENSION = ".png";
    private static final String DESTINATION_DIR = "images";
    private static final String SOURCE_DIR = "screenshots/";

    protected Bitmap createBitmapFromView(@NonNull final Activity activity, @Nullable final View targetView) {
        View v = targetView;
        if (v == null) {
            v = activity.getWindow().getDecorView();
        }
        v.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return bitmap;
    }

    protected boolean saveBitmapToFile(@NonNull Context context, @NonNull Bitmap bitmap, String outputFilePath) throws Exception {
        if (assureScreenshotDirectory(context)) {
            Log.d(LOG_TAG, "Writing screenshot to " + outputFilePath);
            OutputStream outputStream = new FileOutputStream(outputFilePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            return true;
        } else {
            throw new ScreenshotDirectoryNotFoundException(outputFilePath);
        }
    }

    protected boolean assureScreenshotDirectory(@NonNull Context context) {
        boolean created = true;
        File outputDirectory = getOutputDirectoryPath(context);
        if (!outputDirectory.exists()) {
            created = outputDirectory.mkdirs();
        }
        return created;
    }

    @NonNull
    protected File getOutputDirectoryPath(@NonNull Context context) {
        return context.getDir(DESTINATION_DIR, Context.MODE_PRIVATE);
    }

    protected String getOutputFilePath(@NonNull final Context context, final String fileName) {
        return getOutputDirectoryPath(context).getPath() + File.separator + fileName + PNG_EXTENSION;
    }

    protected BitmapFactory.Options getPreferredBitmapOptions() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return options;
    }

    @Nullable
    protected Bitmap loadBitmapFromAsset(Context context, String filePath) throws Exception {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            inputStream = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(inputStream, null, getPreferredBitmapOptions());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to decode bitmap file.", e);
            bitmap = null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Unable to close input stream.", e);
                    bitmap = null;
                }
            }
        }
        return bitmap;
    }

    /**
     * Load a baseline bitmap from the androidTest assets directory.
     *
     * @param context
     * @param testName
     * @return
     * @throws Exception
     */
    @Nullable
    public Bitmap loadBaselineBitmapForComparison(@NonNull Context context, String testName) throws Exception {
        String filePath = SOURCE_DIR + DeviceIdentifier.getDescription(context) + "/" + testName + PNG_EXTENSION;
        return loadBitmapFromAsset(context, filePath);
    }

    /**
     * Capture a bitmap from the given Activity and save it to the screenshots directory.
     *
     * @param activity
     * @param testName
     * @return
     */
    @Nullable
    public Bitmap createBitmapFromActivity(final Activity activity, String testName) throws Exception {
        final Bitmap[] currentActivityBitmap = new Bitmap[1];
        final CountDownLatch latch = new CountDownLatch(1);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentActivityBitmap[0] = createBitmapFromView(activity, null);
                latch.countDown();
            }
        });

        try {
            if (!latch.await(2, TimeUnit.SECONDS)) {
                return null;
            }
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, "createBitmapFromView interrupted.", e);
            return null;
        }

        String outputPath = getOutputFilePath(activity, testName);
        saveBitmapToFile(activity, currentActivityBitmap[0], outputPath);
        return BitmapFactory.decodeFile(outputPath, getPreferredBitmapOptions());
    }

    /**
     * Compare two bitmaps using {@link Bitmap#sameAs(Bitmap)}
     *
     * @param baselineBitmap
     * @param currentBitmap
     * @return
     */
    public boolean compareBitmaps(@Nullable Bitmap baselineBitmap, @Nullable Bitmap currentBitmap) {
        return !(baselineBitmap == null || currentBitmap == null) && baselineBitmap.sameAs(currentBitmap);
    }

    public boolean deleteBitmap(Context context, String testName) {
        final File file = new File(getOutputFilePath(context, testName));
        return file.delete();
    }
}
