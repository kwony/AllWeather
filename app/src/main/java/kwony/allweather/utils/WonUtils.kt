package kwony.allweather.utils

import android.content.Context
import kwony.allweather.R
import java.text.NumberFormat
import java.util.*

object WonUtils {
    const val UK_UNIT = 10000 * 10000
    const val MAN_UNIT = 10000

    fun wonAbsolute(context: Context, money: Int): String {
        return String.format(context.getString(R.string.common_won), NumberFormat.getInstance(Locale.getDefault()).format(money))
    }

    fun wonReadable(context: Context, money:Int): String {
        val ukUnit = money / UK_UNIT
        val manUnit = (money % UK_UNIT) / MAN_UNIT
        val wonUnit = money % MAN_UNIT

        return String.format(
            "%s%s%s%s",
            parseMoneyUnit(ukUnit, context.getString(R.string.common_uk), false),
            parseMoneyUnit(manUnit, context.getString(R.string.common_man), ukUnit > 0),
            parseMoneyUnit(wonUnit, context.getString(R.string.common_money_default), manUnit > 0),
            context.getString(R.string.common_won_unit)
        )
    }

    private fun parseMoneyUnit(money: Int, format: String, needSpace: Boolean = true): String {
        return if (money == 0) {
            ""
        } else {
            if (needSpace) {
                " " + String.format(format, NumberFormat.getInstance(Locale.getDefault()).format(money))
            } else {
                String.format(format, NumberFormat.getInstance(Locale.getDefault()).format(money))
            }
        }
    }
}