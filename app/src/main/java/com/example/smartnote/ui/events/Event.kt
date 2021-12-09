package com.example.smartnote.ui.events

import com.example.smartnote.DateFormatUtils
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class Event : Serializable {
    var date: Long = 0
    lateinit var event: String
//    lateinit var time: String
    var hours: Int = 0
    var minutes: Int = 0

    override fun toString(): String {
        val date = Date(date)
        var df = SimpleDateFormat("dd/MM/yy")
        var dateText = df.format(date)
        val builder = StringBuilder()
        builder.append("Date" + " : " + dateText + "\n")
                .append("Time" + " : " + DateFormatUtils.getFormatedTime(hours, minutes) + "\n")
                .append("Event" + " : " + event + "\n" + "\n")
        return builder.toString()
    }
}
