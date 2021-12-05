package com.example.smartnote

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val imageSmartNote : ImageView = findViewById(R.id.imageSmartNote) as ImageView
        val textViewSmartNote : TextView = findViewById(R.id.smart_note) as TextView
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val atb = AnimationUtils.loadAnimation(this, R.anim.atb)
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

