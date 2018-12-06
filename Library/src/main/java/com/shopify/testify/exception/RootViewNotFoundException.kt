package com.shopify.testify.exception

import android.content.Context
import android.support.annotation.IdRes

class RootViewNotFoundException(context: Context, @IdRes rootViewId: Int) : RuntimeException(String.format("The provided RootViewId {R.id.%s} could not be found in the test harness Activity", context.resources.getResourceEntryName(rootViewId)))
