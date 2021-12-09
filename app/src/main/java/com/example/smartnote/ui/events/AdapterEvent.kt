package com.example.smartnote.ui.events

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.LanguageSupportUtils
import com.example.smartnote. LanguageSupportUtils.Companion.castToLangEvent
import com.example.smartnote.R
import java.text.DateFormat
import java.util.ArrayList

class AdapterEvent(private val items: ArrayList<Event>) :
        RecyclerView.Adapter<AdapterEvent.ViewHolder>() {
    lateinit var context: Context
    lateinit var container : ViewGroup
    lateinit var inflater : LayoutInflater

    companion object {

        const val MY_CONSTANT = 100

    }

    constructor(items: java.util.ArrayList<Event>, context: Context?, container : ViewGroup?, inflater: LayoutInflater) : this(items) {
        if (context != null && container != null && inflater != null) {
            this.context = context
            this.inflater = inflater
            this.container = container
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date_tv: TextView
        var event_tv: TextView
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
                val dialogView = LayoutInflater.from( context).inflate(R.layout.delete_share_layout, null);
                val builder = AlertDialog.Builder(context)
                    .setView(dialogView)
                    .setTitle(context.getString(R.string.delete_the_item))
                val alertDialog = builder.show()
                val imageShare : ImageView = dialogView.findViewById(R.id.shareIv)
                val imageEdit : ImageView = dialogView.findViewById(R.id.editIv)
                val noBtn : TextView = dialogView.findViewById(R.id.noBtn)
                val yesBtn : TextView = dialogView.findViewById(R.id.yesBtn)

                imageShare.setOnClickListener {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val shareBody =
                        LanguageSupportUtils.castToLangEvent(context, items[position].toString())
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.choose_one)))
                }
                imageEdit.setOnClickListener {
                    val dialogEventV = inflater.inflate(R.layout.event_dialog, container, false)
                    val itemViewEvent = inflater.inflate(R.layout.item_event, container, false)
                    var eventEd: EditText = dialogEventV.findViewById(R.id.event)
                    var timeTv : TextView = itemViewEvent.findViewById(R.id.time_event_tv)

                    eventEd.setText(items[position].event)
                    timeTv.setText(items[position].time)

                    val mBuilder = AlertDialog.Builder(context)
                            .setView(dialogEventV)
                    val mAlertDialog = mBuilder.show()

                    val saveBtn: Button = dialogEventV.findViewById(R.id.save_dialog_event)

                    saveBtn.setOnClickListener {
                        mAlertDialog.dismiss()
                        if (eventEd.text.isNotEmpty() && timeTv.text.isNotEmpty()) {
                            event_tv.setText(items[position].event)
                            time_tv.setText(items[position].time)
                        } else {
                            Toast.makeText(context, "Put values!", Toast.LENGTH_LONG).show()
                        }
                    }
                    alertDialog.dismiss()

                    val cancelBtn: Button = dialogEventV.findViewById(R.id.cancel_date_btn)
                    cancelBtn.setOnClickListener() {
                        mAlertDialog.dismiss()
                    }

                }
                noBtn.setOnClickListener{
                    alertDialog.dismiss()
                }
                yesBtn.setOnClickListener {
                    items.remove(items[position])
                    notifyDataSetChanged()
                    alertDialog.dismiss()
                }
            }

        }

    }
}
