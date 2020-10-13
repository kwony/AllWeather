package kwony.allweather.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kwony.allweather.R
import javax.inject.Inject

object RelativeTimeFormatter {
    private const val SECOND = 1000L
    private const val MINUTE = 60 * SECOND
    private const val HOUR = 60 * MINUTE;
    private const val DAY = 24 * HOUR;
    private const val WEEK = 7 * DAY;
    private const val MONTH = 30 * DAY;
    private const val YEAR = DAY * 365;

    fun justNow(context: Context): String = context.getString(R.string.time_now)

    fun minutesAgo(context: Context, diff: Long): String {
        return java.lang.String.format(
            context.getString(R.string.time_minutes).replace("%@", "%s"),
            (diff / MINUTE).toString()
        )
    }

    fun hoursAgo(context: Context, diff: Long): String {
        return java.lang.String.format(
            context.getString(R.string.time_hours).replace("%@", "%s"),
            (diff / HOUR).toString()
        )
    }

    fun daysAgo(context: Context, diff: Long): String {
        return java.lang.String.format(
            context.getString(R.string.time_days).replace("%@", "%s"),
            (diff / DAY).toString()
        )
    }

    fun weeksAgo(context: Context, diff: Long): String {
        return java.lang.String.format(
            context.getString(R.string.time_weeks).replace("%@", "%s"),
            diff / WEEK
        )
    }

    fun monthsAgo(context: Context, diff: Long): String {
        return java.lang.String.format(
            context.getString(R.string.time_months).replace("%@", "%s"),
            diff / MONTH
        )
    }

    fun yearsAgo(context: Context, diff: Long): String {
        return java.lang.String.format(
            context.getString(R.string.time_years).replace("%@", "%s"),
            diff / YEAR
        )
    }
}