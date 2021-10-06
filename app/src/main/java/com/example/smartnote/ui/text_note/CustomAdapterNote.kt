package com.example.smartnote.ui.text_note

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.MainActivity
import com.example.smartnote.R
import com.example.smartnote.ui.sales.Sale
import java.lang.Appendable
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapterNote(private val items: ArrayList<CardNote>) :
        RecyclerView.Adapter<CustomAdapterNote.ViewHolder>() {

    lateinit var context: Context;
    private  var REQUEST_CODE_SPEECH = 100

    constructor(items: ArrayList<CardNote>, context: Context?) : this(items) {

        if (context != null) {
            this.context = context
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val note_tv: TextView
        val voice_btn: ImageButton

        init {
            note_tv = view.findViewById(R.id.note_tv)
            voice_btn = view.findViewById(R.id.voice_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapterNote.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomAdapterNote.ViewHolder, position: Int) {
        holder.run {
            note_tv.setText(items[position].note)

            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(holder.note_tv.context)

                builder.setMessage("Delete the god?")

                builder.setPositiveButton("YES") { dialog, which ->
                    items.remove(items[position])
                    notifyDataSetChanged()

                }
                builder.setNegativeButton("No") { dialog, which ->

                }
                builder.setNeutralButton("Share") { dialog, which ->
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val shareBody = items[position].toString()
                    val shareSub = "items[position]"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    context.startActivity(Intent.createChooser(shareIntent, "choose one"))
                    true
                }
                val dialog: AlertDialog = builder.create()

                dialog.show()
                notifyDataSetChanged()
            }
            voice_btn.setOnClickListener{
                speak()
            }

        }

    }

    private fun speak() {
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi, speak something")

        try{

            startActivityForResult(<MyActivity> context),mIntent, REQUEST_CODE_SPEECH)
        }
        catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when(requestCode) {
//            REQUEST_CODE_SPEECH ->{
//                if(resultCode == Activity.RESULT_OK && null != data) {
//                    val result = data.getStringArrayExtra(RecognizerIntent.EXTRA_RESULTS)
//                    note_tv.text = result?.get(0) ?: "test"
//                    Log.e("natlight", "result")
//                }
//            }
//        }
//    }

}