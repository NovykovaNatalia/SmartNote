package com.nnnd.smartnote.ui.draws

import android.graphics.Paint
import java.text.SimpleDateFormat
import java.util.*

class PaintItem {
    lateinit var paint: Paint
//    lateinit var paths: ArrayList<FingerPathItem>
    var date: Long = 0L;
    lateinit var title: String

    override fun toString(): String {
        val date = Date(date)
        var df = SimpleDateFormat("dd/MM/yy")
        var dateText = df.format(date)
        val builder = StringBuilder()
        builder.append("Title" + " : " + title  + "\n")
            .append("Date" + " : " + dateText + "\n" + "\n")

        return builder.toString()
    }
}