package com.example.smartnote.ui.text_note

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.LanguageSupportUtils
import com.example.smartnote.R
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapterNote(private val items: ArrayList<CardNote>) :
        RecyclerView.Adapter<CustomAdapterNote.ViewHolder>() {

    lateinit var context: Context;

    constructor(items: ArrayList<CardNote>, context: Context?) : this(items) {

        if (context != null) {
            this.context = context
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val note_tv: TextView

        init {
            note_tv = view.findViewById(R.id.note_tv)
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

                builder.setMessage(context.getString(R.string.delete_the_note))

                builder.setPositiveButton(R.string.yes) { dialog, which ->
                    items.remove(items[position])
                    notifyDataSetChanged()
                }

                builder.setNegativeButton(R.string.no) { dialog, which ->

                }
                builder.setNeutralButton(R.string.share) { dialog, which ->
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val shareBody = LanguageSupportUtils.castToLangNotes(context, items[position].toString())
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    context.startActivity(Intent.createChooser(shareIntent, "choose one"))
                    true
                }
                val dialog: AlertDialog = builder.create()

                dialog.show()
                notifyDataSetChanged()
            }

        }
    }


}