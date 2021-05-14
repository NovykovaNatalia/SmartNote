package com.example.smartnote.ui.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.R

class PrivacyPolicy : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


        //crating an arraylist to store users using the data class user
        val users = ArrayList<User>()

        //adding some dummy data to the list
        users.add(User("Privacy Policy", "What information do we collect?\n" +
                "\n" +
                "When ordering or registering on our apps or website, as appropriate, you may " +
                "be asked to enter your e-mail address. Some apps or services may acquire your location data, " +
                "but this data is obtained after a clear explanation and your consent to start using it."))
        users.add(User("What do we use your information for?", "What information do we collect?\n" +
                "\n" +
                "Any of the information we collect from you may be used of the following ways:\n" +
                "- to personalize your experience (your information helps us to better respond to your individual needs),\n" +
                "\n" +
                "- to show data on your device, record sport activities and calculate sport parameters, support ads.\n"))
        users.add(User("How do we protect your information??", "We implement a variety of security measures to maintain the safety of your personal information when you enter, submit or access your personal information.\n" +
                "\n" +
                "Do we use cookies?\n" +
                "\n" +
                "We do not use cookies.\n"))


        //creating our adapter
        val adapter = CustomAdapter(users)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }
}