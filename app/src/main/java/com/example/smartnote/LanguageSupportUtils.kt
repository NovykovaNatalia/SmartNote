package com.example.smartnote

import android.content.Context

class LanguageSupportUtils  {
    companion object {
        fun castToLangEvent(context: Context, s: String): String? {
            var s = s.replace("Date", context.getString(R.string.date_string))
            s = s.replace("Time", context.getString(R.string.time_string))
            s = s.replace("Event", context.getString(R.string.event_string))
            return s
        }
//        fun castToLangShop(context: Context, s: String): String? {
//            var s = s.replace("Date", context.getString(R.string.date_string))
//            s = s.replace("Time", context.getString(R.string.time_string))
//            s = s.replace("Event", context.getString(R.string.event_string))
//            s = s.replace("Event", context.getString(R.string.event_string))
//            return s
//        }
    }
}