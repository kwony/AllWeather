package kwony.allweather.utils

import android.content.Context
import kwony.allweather.R
import java.text.NumberFormat
import java.util.*

object NumberUtils {
    fun wonString(context: Context, money: Int): String {
        return String.format(context.getString(R.string.common_won), NumberFormat.getInstance(Locale.getDefault()).format(money))
    }
}