package com.android.main

import android.annotation.SuppressLint
import com.llj.baselib.IOTLib
import com.llj.baselib.IOTLib.getUcb
import java.text.SimpleDateFormat
import java.util.*

fun getUserId() = IOTLib.loadUserData().first

@SuppressLint("SimpleDateFormat")
fun getDateToString(milSecond: Long, pattern: String?): String? {
    val date = Date(milSecond)
    val format = SimpleDateFormat(pattern)
    return format.format(date)
}

fun time2Float(time: Long): Float {
    return (time % 1000000).toFloat()
}

fun time2String(time: Long): String {
    val cTime = System.currentTimeMillis().toString()
    val newTime = (cTime.substring(0..(cTime.lastIndex - 6)) + time).toLong()
    return getDateToString(newTime, "hh:mm:ss") ?: "00:00"
}