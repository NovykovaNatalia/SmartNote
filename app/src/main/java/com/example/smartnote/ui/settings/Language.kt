package com.example.smartnote.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smartnote.MainActivity
import com.example.smartnote.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class Language : AppCompatActivity() {
    lateinit var locale: Locale
    private var currentLanguage = "en"
    private var currentLang: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.language)
        var spinner : Spinner = findViewById(R.id.spinner)
        var currentLang: String? = null
            title = "KotlinApp"
            currentLanguage = intent.getStringExtra(currentLang).toString()
            val list = ArrayList<String>()
            list.add("Select Language")
            list.add("English")
            list.add("Ukrainian")
            list.add("Russian")
            list.add("Polish")
            val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    when (position) {
                        0 -> {
                        }
                        1 -> setLocale("en")
                        2 -> setLocale("uk")
                        3 -> setLocale("ru")
                        4 -> setLocale("pl")
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
        private fun setLocale(localeName: String) {
            if (localeName != currentLanguage) {
                locale = Locale(localeName)
                val res = resources
                val dm = res.displayMetrics
                val conf = res.configuration
                conf.locale = locale
                res.updateConfiguration(conf, dm)
                val refresh = Intent(
                        this,
                        MainActivity::class.java
                )
                refresh.putExtra(currentLang, localeName)
                startActivity(refresh)
            } else {
                Toast.makeText(
                        this, "Language, , already, , selected)!", Toast.LENGTH_SHORT).show();
            }
        }
        override fun onBackPressed() {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
            exitProcess(0)
        }
    }
