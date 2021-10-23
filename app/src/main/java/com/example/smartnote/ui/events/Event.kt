package com.example.smartnote.ui.events

import java.io.Serializable
import java.util.*

class Event : Serializable {
    var date_ev: Long = 0
    lateinit var event: String
    lateinit var time: String

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Date" + " : " + date_ev + "\n")
                .append("Time" + " : " + time + "\n")
                .append("Event" + " : " + event + "\n" + "\n")
        return builder.toString()
    }
}
