package com.giant_giraffe.utility

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.lang.Exception

object DateTimeUtility {

    private val formatter = DateTimeFormat
        .forPattern("yyyy-MM-dd HH:mm:ss")

    /**
     * For PostgreSQL
     */
    fun convertToString(dateTime: DateTime?): String? {
        return try {
            if (dateTime == null) {
                return null
            }

            dateTime.toString(formatter)
        } catch (e: Exception) {
            null
        }
    }

    fun tryParse(dateTimeString: String?): DateTime? {
        return try {
            formatter
                .parseDateTime(dateTimeString)
        } catch (e: Exception) {
            null
        }
    }

}