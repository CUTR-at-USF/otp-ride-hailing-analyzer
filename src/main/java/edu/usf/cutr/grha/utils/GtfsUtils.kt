package edu.usf.cutr.grha.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object GtfsUtils {

    @JvmStatic
     fun getDateFromTimeStamp(timeStamp: String, pattern: String): Date {
        val format = SimpleDateFormat(pattern, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("America/New_York")
        var date = Date()
        try {
            date = format.parse(timeStamp)
        } catch (parseException: ParseException) {
             println(parseException.message)
        }
        return date
    }

    @JvmStatic
    fun getTimeInSeconds(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return 3600 * calendar[Calendar.HOUR_OF_DAY] + 60 * calendar[Calendar.MINUTE]
    }
}