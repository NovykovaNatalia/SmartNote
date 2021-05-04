package com.example.smartnote.ui.settings

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import com.example.smartnote.MyApplication
import com.example.smartnote.R

class Settings : AppCompatActivity() {
    lateinit var  switchMode: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        switchMode = findViewById(R.id.switchMode)

        val appSettingPref: SharedPreferences = getSharedPreferences("AppSettingPref", 0)!!
        val sharedPrefsEdit:SharedPreferences.Editor = appSettingPref.edit()

        val isNightModeOn: Boolean = appSettingPref.getBoolean("NightMode", false)
        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            switchMode.text = "Disable Dark Mode"
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            switchMode.text = "Enable Dark Mode"
        }
        switchMode.setOnClickListener() {
            if(isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit.putBoolean("NightMode", false)
                sharedPrefsEdit.apply()
                switchMode.text = "Enable Dark Mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit.putBoolean("NightMode", true)
                sharedPrefsEdit.apply()
                switchMode.text = "Disable Dark Mode"
            }
        }
    }

}