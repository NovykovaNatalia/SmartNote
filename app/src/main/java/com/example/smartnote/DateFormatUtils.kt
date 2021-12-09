package com.example.smartnote

class DateFormatUtils  {
    companion object {
        fun getFormatedTime(hour:Int, minute:Int): String {
            var hour = hour
            var am_pm = ""
            when {
                hour == 0 -> {
                    hour += 12
                    am_pm = "AM"
                }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> {
                    hour -= 12
                    am_pm = "PM"
                } else -> am_pm = "AM"
            }
            val msgHour = if (hour < 10) "0" + hour else hour
            val msgMin = if (minute < 10) "0" + minute else minute
            val msg = "$msgHour : $msgMin $am_pm"
            return msg
        }
    }
}