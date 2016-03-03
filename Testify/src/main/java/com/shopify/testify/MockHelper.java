package com.shopify.testify;

import android.content.Context;

/**
 * Created by eapache on 2015-12-02.
 */
public class MockHelper {
    public static void initMockito(Context context) {
        // Required by Mockito
        // Fixes "dexcache == null (and no default could be found; consider setting the 'dexmaker.dexcache' system property)"
        // See https://code.google.com/p/dexmaker/issues/detail?id=2
        System.setProperty("dexmaker.dexcache", context.getCacheDir().getPath());
    }
}
