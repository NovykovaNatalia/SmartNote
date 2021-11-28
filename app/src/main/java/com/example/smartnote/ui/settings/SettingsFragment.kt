package com.example.smartnote.ui.settings

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.smartnote.R
import android.widget.ArrayAdapter
import com.example.smartnote.MainActivity
import java.util.*
import kotlin.system.exitProcess

class SettingsFragment : Fragment() {

    lateinit var locale: Locale
    private var currentLanguage = "en"
    private var currentLang: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        var switchModeFr: Switch = view.findViewById(R.id.switchModeFr)
        var shareFr: TextView = view.findViewById(R.id.shared_tv_fr)
        var about_snFr: TextView = view.findViewById(R.id.about_sn_tv_fr)
        var contact: TextView = view.findViewById(R.id.contact)
        var rate: TextView = view.findViewById(R.id.rate)
        var spinner: Spinner = view.findViewById(R.id.spinner)

        var currentLang: String? = null
        activity?.title = "KotlinApp"
        currentLanguage = activity?.intent?.getStringExtra(currentLang).toString()
        val list = ArrayList<String>()
        list.add(getString(R.string.select_lang))
        list.add(getString(R.string.english))
        list.add(getString(R.string.ukrainian))
        list.add(getString(R.string.russian))
        list.add(getString(R.string.polish))

        val adapter: ArrayAdapter<String> = ArrayAdapter(context!!,R.layout.support_simple_spinner_dropdown_item, list)
        spinner.adapter = adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                    }
                    1 -> setLocale("en")
                    2 -> setLocale("uk")
                    3 -> setLocale("ru")
                    4 -> setLocale("pl")
                }
                adapter?.notifyDataSetChanged()
            }
        }
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: int, id: Long) {
//                when (position) {
//                    0 -> {
//                    }
//                    1 -> setLocale("en")
//                    2 -> setLocale("uk")
//                    3 -> setLocale("ru")
//                    4 -> setLocale("pl")
//                }
//                adapter?.notifyDataSetChanged()
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//        }

        about_snFr.setOnClickListener {
            val intent = Intent(activity, AboutSmartNote::class.java)
            intent.putExtra("key", "Kotlin")
            startActivity(intent)
        }

        val viewRate = inflater.inflate(R.layout.rate_dialog, container, false)
        val mRatingBar = viewRate.findViewById<View>(R.id.ratingBar) as RatingBar
        val mRatingScale = viewRate.findViewById<View>(R.id.tvRatingScale) as TextView

        rate.setOnClickListener {
            val mDialogViewRate = LayoutInflater.from(context).inflate(R.layout.rate_dialog, null)
            val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogViewRate)
                    .setTitle(R.string.rate_us)
            mBuilder.show()

            mRatingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { ratingBar, v, b ->
                mRatingScale.text = v.toString()
                when (ratingBar.rating.toInt()) {
                    1 -> mRatingScale.text = getString(R.string.very_bad)
                    2 -> mRatingScale.text = getString(R.string.need_some_impovement)
                    3 -> mRatingScale.text = getString(R.string.good)
                    4 -> mRatingScale.text = getString(R.string.great)
                    5 -> mRatingScale.text = getString(R.string.awesome)
                    else -> mRatingScale.text = ""
                }
            }
        }

        contact.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("natlight.todo@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Your subject here...")
            intent.putExtra(Intent.EXTRA_TEXT, "Your message here...")
            startActivity(intent)
        }

        shareFr.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val shareBody = "Your body here"
            val shareSub = "Your subject here"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
            true
        }

        val appSettingPref: SharedPreferences = activity?.getSharedPreferences("AppSettingPref", 0)!!
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPref.edit()

        val isNightModeOn: Boolean = appSettingPref.getBoolean("NightMode", false)


        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            switchModeFr.text = getString(R.string.disable_dark_mode)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            switchModeFr.text = getString(R.string.enable_dark_mode)
        }

        switchModeFr.setOnClickListener() {
            if (isNightModeOn) {
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

    private fun setLocale(localeName: String) {
        if (localeName != currentLanguage) {
            locale = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)
            val refresh = Intent(
                    context,
                    MainActivity::class.java
            )
            refresh.putExtra(currentLang, localeName)
            startActivity(refresh)
        } else {
            Toast.makeText(
                    context, "Language already selected)!", Toast.LENGTH_SHORT).show();
        }
    }

    fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        activity?.finish()
        exitProcess(0)
    }

}