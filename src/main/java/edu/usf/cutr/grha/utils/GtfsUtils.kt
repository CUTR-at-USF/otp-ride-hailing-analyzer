package edu.usf.cutr.grha.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object GtfsUtils {

    @JvmStatic
     fun getDateFromTimeStamp(timeStamp: String, pattern: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return LocalDateTime.parse(timeStamp, formatter)
    }
}