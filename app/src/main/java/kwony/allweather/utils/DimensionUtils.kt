package kwony.allweather.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

object DimensionUtils {
    fun dp2px(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        )
    }

    fun px2dp(context: Context, px: Float): Float {
        val displayMetrics = context.resources.displayMetrics
        return px / (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun sp2px(context: Context, sp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            context.resources.displayMetrics
        )
    }

    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun screenSize(context: Context): Screen {
        val metrics = context.resources.displayMetrics
        val height = metrics.heightPixels
        val width = metrics.widthPixels
        return Screen(height, width)
    }

    class Screen internal constructor(val height: Int, val width: Int)
}