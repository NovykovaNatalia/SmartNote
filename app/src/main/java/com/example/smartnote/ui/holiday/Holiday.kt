package com.example.smartnote.ui.holiday

import java.io.Serializable

class Holiday : Serializable {
    lateinit var date_ev: String
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
