package com.example.smartnote.ui.holiday

import java.io.Serializable

class Holiday : Serializable {
    lateinit var date : String
    lateinit var event : String

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Date" + " : " + date + "\n")
            .append("Event" + " : " + event + "\n")
        return builder.toString()
    }
}
