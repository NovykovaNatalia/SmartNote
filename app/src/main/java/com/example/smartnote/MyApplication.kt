package com.example.smartnote

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MyApplication.Companion.setContext(this);
        setDayNightMode();
    }

    companion object {
        private lateinit var context: Context

        fun setContext(con: Context) {
            context=con
        }

        fun getContext(): Context? {
            return context
        }

        fun setDayNightMode() {
            val appSettingPref: SharedPreferences = context.getSharedPreferences("AppSettingPref", 0)!!
            val sharedPrefsEdit:SharedPreferences.Editor = appSettingPref.edit()

            val isNightModeOn: Boolean = appSettingPref.getBoolean("NightMode", false)
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}