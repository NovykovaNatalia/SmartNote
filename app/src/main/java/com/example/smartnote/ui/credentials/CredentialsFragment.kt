package com.example.smartnote.ui.credentials

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CredentialsFragment : Fragment() {
    lateinit var floatingActionButtonCredentials: FloatingActionButton
    lateinit var saveActionButtonCredentials: Button
    lateinit var cancelActionButtonCredentials: Button
    lateinit var credentials: EditText
    lateinit var reference: EditText
    lateinit var credential_tv: TextView
    lateinit var reference_tv: TextView
    lateinit var recyclerViewCredentials: RecyclerView
    lateinit var searchViewCredentials: SearchView
    var cardListCredentials: ArrayList<CardCredentials> = ArrayList()
    var displayCardListCredentials: ArrayList<CardCredentials> = ArrayList()
    lateinit var customAdapterCredentials: CustomAdapterCredentials


    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_credentials, container, false)

        searchViewCredentials = root.findViewById(R.id.searchViewCredentials)
        recyclerViewCredentials = root.findViewById<RecyclerView>(R.id.recyclerViewCredentials)
        recyclerViewCredentials.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerViewCredentials.adapter = CustomAdapterCredentials(displayCardListCredentials)
        customAdapterCredentials = CustomAdapterCredentials(displayCardListCredentials)
        recyclerViewCredentials.adapter = customAdapterCredentials
        customAdapterCredentials.notifyDataSetChanged()
        floatingActionButtonCredentials = root.findViewById(R.id.floating_btn_credentials)

        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val atb = AnimationUtils.loadAnimation(context, R.anim.atb)
        val btt = AnimationUtils.loadAnimation(context, R.anim.btt)
        val btn = AnimationUtils.loadAnimation(context, R.anim.btn)

        searchViewCredentials.startAnimation(ttb)
        recyclerViewCredentials.startAnimation(btt)
        floatingActionButtonCredentials.startAnimation(ttb)

        floatingActionButtonCredentials.setOnClickListener {
            val mDialogViewCredentials = LayoutInflater.from(context).inflate(R.layout.credentials_dialog, null);
            val mItemViewCredentials = LayoutInflater.from(context).inflate(R.layout.item_credentials, null)

            val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogViewCredentials)
                    .setTitle("Credentials")
            val mAlertDialog = mBuilder.show()

            credentials = mDialogViewCredentials.findViewById(R.id.credential)
            reference = mDialogViewCredentials.findViewById(R.id.reference)

            credential_tv = mItemViewCredentials.findViewById(R.id.name_credentials_tv)
            reference_tv = mItemViewCredentials.findViewById(R.id.reference_credential_tv)

            cancelActionButtonCredentials = mDialogViewCredentials.findViewById(R.id.cancel_dialog_credentials)
            saveActionButtonCredentials = mDialogViewCredentials.findViewById(R.id.save_dialog_credentials);

            saveActionButtonCredentials.setOnClickListener {
                mAlertDialog.dismiss()
                var cardCredentils = CardCredentials()
                cardCredentils.credential = credentials.text.toString()
                cardCredentils.reference = reference.text.toString()


                cardListCredentials.add(cardCredentils)
                displayCardListCredentials.add(cardCredentils)

                println(cardListCredentials.toMutableList())
                println(cardListCredentials)
                customAdapterCredentials.notifyDataSetChanged()

            }
            cancelActionButtonCredentials.setOnClickListener() {
                cancelActionButtonCredentials.startAnimation(ttb)
                mAlertDialog.dismiss()
            }

        }

        return root
    }
}

