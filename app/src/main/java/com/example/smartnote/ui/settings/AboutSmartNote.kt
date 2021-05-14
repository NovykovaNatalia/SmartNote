package com.example.smartnote.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.smartnote.R

class AboutSmartNote : AppCompatActivity() {
    lateinit var version: TextView
    lateinit var privacyPolicy: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_smart_note)

        version = findViewById(R.id.version)
        privacyPolicy = findViewById(R.id.privacy_policy)

        version.setOnClickListener{
            val intent = Intent(this, Version::class.java)
            startActivity(intent)
        }
        privacyPolicy.setOnClickListener{
            val intent = Intent(this, PrivacyPolicy::class.java)
            startActivity(intent)
        }
    }
}