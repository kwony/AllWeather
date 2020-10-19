package kwony.allweather.utils

import android.util.Log
import kwony.allweather.BuildConfig

internal object Logger {
    private const val TARGET_INDEX: Int = 5

    @JvmStatic
    fun e(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, getMessage(msg))
        }
    }

    @JvmStatic
    fun d(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, getMessage(msg))
        }
    }

    private val tag: String
        get() {
            val className: String = className
            val packageIndex: Int = className.lastIndexOf('.')
            return className.substring(packageIndex + 1)
        }

    private val methodName: String
        get() = Thread.currentThread().stackTrace[TARGET_INDEX].methodName

    private val lineNumber: Int
        get() = Thread.currentThread().stackTrace[TARGET_INDEX].lineNumber

    private val className: String
        get() = Thread.currentThread().stackTrace[TARGET_INDEX].className

    private fun getMessage(msg: String): String =
        StringBuilder().append(msg)
            .append("[$lineNumber]")
            .append("[${Thread.currentThread().name}]")
            .append("[$methodName]").toString()
}