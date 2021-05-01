package com.example.smartnote.ui.text_note

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.R

class CustomAdapterNote(private val items: ArrayList<CardNote> ):
        RecyclerView.Adapter<CustomAdapterNote.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val note_tv: TextView

        init {
            // Define click listener for the ViewHolder's View.
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

                builder.setMessage("Delete the god?")

                builder.setPositiveButton("YES") { dialog, which ->
                    items.remove(items[position])
                    notifyDataSetChanged()

                }
                builder.setNegativeButton("No") { dialog, which ->

                }
                val dialog: AlertDialog = builder.create()

                dialog.show()
                notifyDataSetChanged()
            }

        }

    }

}