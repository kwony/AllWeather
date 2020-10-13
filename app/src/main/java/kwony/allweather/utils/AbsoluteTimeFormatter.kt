package kwony.allweather.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object AbsoluteTimeFormatter {

    @SuppressLint("SimpleDateFormat")
    fun printDate(mills: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(mills)
    }
}