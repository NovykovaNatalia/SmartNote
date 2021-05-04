package com.example.smartnote.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import com.example.smartnote.MyApplication
import com.example.smartnote.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SettingsFragment : Fragment() {
    lateinit var float_btn: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    val  view =  inflater.inflate(R.layout.fragment_settings, container, false)

        float_btn = view.findViewById(R.id.floating_btn_setting_activity)
        float_btn.setOnClickListener {
           var intent = Intent(activity, Settings::class.java)
            startActivity(intent)
        }

        return view
    }

}