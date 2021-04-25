package com.example.smartnote2.ui.credentials

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote2.R
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
        recyclerViewCredentials = root.findViewById<RecyclerView>(R.id.recyclerViewCredentials)

        recyclerViewCredentials.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerViewCredentials.adapter = CustomAdapterCredentials(displayCardListCredentials)

        customAdapterCredentials = CustomAdapterCredentials(displayCardListCredentials)
        recyclerViewCredentials.adapter = customAdapterCredentials
        customAdapterCredentials.notifyDataSetChanged()
        floatingActionButtonCredentials = root.findViewById(R.id.floating_btn_credentials)
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
                mAlertDialog.dismiss()
            }

        }

        return root
    }
}

