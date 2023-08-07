package com.example.weathernewsapp.utlis

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun getCurrentDateTime(): String {
    val milliseconds = Calendar.getInstance().timeInMillis
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = Date(milliseconds)
    return sdf.format(date)
}

