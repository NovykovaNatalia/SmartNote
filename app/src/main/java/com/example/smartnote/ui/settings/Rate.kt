package com.example.smartnote.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.example.smartnote.R


class Rate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rate_dialog)
        val mRatingBar = findViewById<View>(R.id.ratingBar) as RatingBar
        val mRatingScale = findViewById<View>(R.id.tvRatingScale) as TextView
        val mSendFeedback: Button = findViewById<View>(R.id.btnSubmit) as Button

        mRatingBar.onRatingBarChangeListener = OnRatingBarChangeListener { ratingBar, v, b ->
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
}