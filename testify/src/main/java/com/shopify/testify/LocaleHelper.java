package com.shopify.testify;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.test.InstrumentationRegistry;

import java.util.Locale;

class LocaleHelper {

    private LocaleHelper() {
    }

    public static void setTestLocale(Locale locale) {
        Locale.setDefault(locale);
        setResourcesLocale(InstrumentationRegistry.getTargetContext().getResources(), locale);
        setResourcesLocale(Resources.getSystem(), locale);
    }

    @SuppressWarnings("deprecation")
    private static void setResourcesLocale(Resources resources, Locale locale) {
        final Configuration configuration = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

}
