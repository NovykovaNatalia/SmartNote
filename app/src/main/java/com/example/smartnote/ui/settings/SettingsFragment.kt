package com.example.smartnote.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.smartnote.MyApplication
import com.example.smartnote.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SettingsFragment : Fragment() {
    lateinit var  switchModeFr: Switch
    lateinit var shareFr: TextView
    lateinit var about_snFr: TextView
    lateinit var frame_settings: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    val  view =  inflater.inflate(R.layout.fragment_settings, container, false)

        switchModeFr = view.findViewById(R.id.switchModeFr)
        shareFr = view.findViewById(R.id.shared_tv_fr)
        about_snFr = view.findViewById(R.id.about_sn_tv_fr)
        frame_settings = view.findViewById(R.id.frame_settings)

        shareFr.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val shareBody = "Your body hear"
            val shareSub = "Your subject hear"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
            true
        }
        about_snFr.setOnClickListener {
            val intent = Intent(activity, AboutSmartNote::class.java)
            intent.putExtra("key", "Kotlin")
            startActivity(intent)
        }

        val appSettingPref: SharedPreferences = activity?.getSharedPreferences("AppSettingPref", 0)!!
        val sharedPrefsEdit:SharedPreferences.Editor = appSettingPref.edit()

        val isNightModeOn: Boolean = appSettingPref.getBoolean("NightMode", false)
        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            switchModeFr.text = "Disable Dark Mode"
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            switchModeFr.text = "Enable Dark Mode"
        }
        switchModeFr.setOnClickListener() {
            if(isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit.putBoolean("NightMode", false)
                sharedPrefsEdit.apply()
                switchModeFr.text = "Enable Dark Mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit.putBoolean("NightMode", true)
                sharedPrefsEdit.apply()
                switchModeFr.text = "Disable Dark Mode"
            }
        }

    return view
    }

}