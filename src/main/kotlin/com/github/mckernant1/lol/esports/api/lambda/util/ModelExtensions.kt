package com.github.mckernant1.lol.esports.api.lambda.util

import com.github.mckernant1.lol.esports.api.Tournament
import java.text.SimpleDateFormat
import java.util.Date

fun Tournament.startDateAsDate(): Date? {
    return try {
        SimpleDateFormat("yyyy-MM-dd").parse(this.startDate)
    } catch (e: Exception) {
        null
    }
}

fun Tournament.endDateAsDate(): Date? {
    return try {
        SimpleDateFormat("yyyy-MM-dd").parse(this.endDate)
    } catch (e: Exception) {
        null
    }
}
