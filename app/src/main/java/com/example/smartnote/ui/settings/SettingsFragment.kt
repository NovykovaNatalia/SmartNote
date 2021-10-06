package com.example.smartnote.ui.settings

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

class SettingsFragment : Fragment() {
    lateinit var  switchModeFr: Switch
    lateinit var shareFr: TextView
    lateinit var about_snFr: TextView
    lateinit var frame_settings: FrameLayout
    lateinit var contact: TextView
    lateinit var rate: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    val  view =  inflater.inflate(R.layout.fragment_settings, container, false)
    val viewRate = inflater.inflate(R.layout.rate_dialog, container, false)

        switchModeFr = view.findViewById(R.id.switchModeFr)
        shareFr = view.findViewById(R.id.shared_tv_fr)
        about_snFr = view.findViewById(R.id.about_sn_tv_fr)
        frame_settings = view.findViewById(R.id.frame_settings)
        contact = view.findViewById(R.id.contact)
        rate = view.findViewById(R.id.rate)

        val mRatingBar = viewRate.findViewById<View>(R.id.ratingBar) as RatingBar
        val mRatingScale = viewRate.findViewById<View>(R.id.tvRatingScale) as TextView
        val mSendFeedback: Button = viewRate.findViewById<View>(R.id.btnSubmit) as Button

        rate.setOnClickListener {
            val mDialogViewRate = LayoutInflater.from(context).inflate(R.layout.rate_dialog, null)
            val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogViewRate)
                    .setTitle("Rate us!")
            val mAlertDialog = mBuilder.show()

            mRatingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { ratingBar, v, b ->
                mRatingScale.text = v.toString()
                when (ratingBar.rating.toInt()) {
                    1 -> mRatingScale.text = "Very bad"
                    2 -> mRatingScale.text = "Need some improvement"
                    3 -> mRatingScale.text = "Good"
                    4 -> mRatingScale.text = "Great"
                    5 -> mRatingScale.text = "Awesome. I love it"
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