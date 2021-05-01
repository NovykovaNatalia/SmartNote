package com.example.smartnote

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    lateinit var imageSmartNote: ImageView
    lateinit var textViewSmartNote: TextView
    lateinit var frameLayout: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        imageSmartNote = findViewById(R.id.imageSmartNote) as ImageView
        textViewSmartNote = findViewById(R.id.smart_note) as TextView
        frameLayout = findViewById<FrameLayout>(R.id.layout)

        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val atb = AnimationUtils.loadAnimation(this, R.anim.atb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
        val btn = AnimationUtils.loadAnimation(this, R.anim.btn)
        textViewSmartNote.startAnimation(ttb)
        imageSmartNote.startAnimation(atb)

        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}

