package com.example.smartnote.ui.events

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.R
import java.text.DateFormat
import java.util.ArrayList

class AdapterEvent(private val items: ArrayList<Event>) :
        RecyclerView.Adapter<AdapterEvent.ViewHolder>() {
    lateinit var context: Context;

    companion object {

        const val MY_CONSTANT = 100

    }

    constructor(items: java.util.ArrayList<Event>, context: Context?) : this(items) {
        if (context != null) {
            this.context = context
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date_tv: TextView
        val event_tv: TextView
        val time_tv: TextView

        init {
            date_tv = view.findViewById(R.id.date_event_tv)
            event_tv = view.findViewById(R.id.text_event_tv)
            time_tv = view.findViewById(R.id.time_event_tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterEvent.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AdapterEvent.ViewHolder, position: Int) {
        holder.run {
            val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
            event_tv.setText(items[position].event)
            date_tv.setText(dateFormatter.format(items[position].date_ev))
            time_tv.setText(items[position].time)

            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(holder.date_tv.context)

                builder.setMessage(context.getString(R.string.delete_event))

                builder.setPositiveButton(R.string.yes) { dialog, which ->
                    items.remove(items[position])
                    notifyDataSetChanged()

                }
                builder.setNegativeButton(R.string.no) { dialog, which ->

                }

                builder.setNeutralButton(R.string.share) { dialog, which ->
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val shareBody = customizeOutput(items[position].toString())
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

        }

    }

    private fun customizeOutput(s: String): String? {
        var s = s.replace("Date", context.getString(R.string.date_string))
        s = s.replace("Time", context.getString(R.string.time_string))
        s = s.replace("Event", context.getString(R.string.event_string))
        return s

    }

}
