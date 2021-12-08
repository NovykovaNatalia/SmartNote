package com.example.smartnote.ui.events

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class Event : Serializable {
    var date_ev: Long = 0
    lateinit var event: String
    lateinit var time: String

    override fun toString(): String {
        val date = Date(date_ev)
        var df = SimpleDateFormat("dd/MM/yy")
        var dateText = df.format(date)
        val builder = StringBuilder()
        builder.append("Date" + " : " + dateText + "\n")
                .append("Time" + " : " + time + "\n")
                .append("Event" + " : " + event + "\n" + "\n")
        return builder.toString()
    }
}
