package com.example.smartnote

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MyApplication.Companion.setContext(this);
    }

    companion object {
        private lateinit var context: Context

        fun setContext(con: Context) {
            context=con
        }

        fun getContext(): Context? {
            return context
        }
    }
}