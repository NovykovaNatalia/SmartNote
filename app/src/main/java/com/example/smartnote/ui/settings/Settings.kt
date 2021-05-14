package com.example.smartnote.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.smartnote.MyApplication
import com.example.smartnote.R
import org.w3c.dom.Text

class Settings : AppCompatActivity() {
    lateinit var  switchMode: Switch
    lateinit var share: TextView
    lateinit var about_sn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        switchMode = findViewById(R.id.switchMode)
        share = findViewById(R.id.shared_tv)
        about_sn = findViewById(R.id.about_sn_tv)

        share.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val shareBody = "Your body hear"
            val shareSub = "Your subject hear"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
            true
        }
        about_sn.setOnClickListener {
            val intent = Intent(this, AboutSmartNote::class.java)
            intent.putExtra("key", "Kotlin")
            startActivity(intent)
        }

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