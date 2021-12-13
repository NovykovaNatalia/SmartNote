package com.nnnd.smartnote.ui.events

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.nnnd.smartnote.DateFormatUtils
import com.nnnd.smartnote.LanguageSupportUtils
import com.nnnd.smartnote.R
import java.sql.Time
import java.text.DateFormat
import java.util.*

class AdapterEvent(private val items: ArrayList<Event>) :
        RecyclerView.Adapter<AdapterEvent.ViewHolder>() {
    lateinit var context: Context
    lateinit var container : ViewGroup
    companion object {

        const val MY_CONSTANT = 100

    }

    constructor(items: java.util.ArrayList<Event>, context: Context?, container : ViewGroup?) : this(items) {
        if (context != null && container != null) {
            this.context = context
            this.container = container
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTv: TextView
        var eventTv: TextView
        val timeTv: TextView
        val ll: LinearLayout

        init {
            dateTv = view.findViewById(R.id.date_event_tv)
            eventTv = view.findViewById(R.id.text_event_tv)
            timeTv = view.findViewById(R.id.time_event_tv)
            ll = view.findViewById(R.id.dot3_ll_id)
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

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: AdapterEvent.ViewHolder, position: Int) {
        holder.run {
            val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
            eventTv.setText(items[position].event)
            dateTv.setText(dateFormatter.format(items[position].date))
            timeTv.setText(DateFormatUtils.getFormatedTime(items[position].hours, items[position].minutes))

            ll.setOnClickListener {
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
                    val dialogEventV = LayoutInflater.from( context).inflate(R.layout.event_dialog, container, false)

                    var eventEd: EditText = dialogEventV.findViewById(R.id.event)
                    var timeTp : TimePicker = dialogEventV.findViewById(R.id.timePicker)

                    eventEd.setText(items[position].event)
                    var time:Time
                    timeTp.currentHour = items[position].hours
                    timeTp.currentMinute = items[position].minutes

                    val builder = AlertDialog.Builder(context)
                            .setView(dialogEventV)
                    val ad = builder.show()

                    val saveBtn: Button = dialogEventV.findViewById(R.id.save_dialog_event)

                    saveBtn.setOnClickListener {
                        ad.dismiss()
                        if (eventEd.text.isNotEmpty()) {
                            items[position].event = eventEd.text.toString()
                            items[position].hours = timeTp.hour
                            items[position].minutes = timeTp.minute
                        } else {
                            Toast.makeText(context, "Put values!", Toast.LENGTH_LONG).show()
                        }
                        notifyDataSetChanged();
                    }
                    alertDialog.dismiss()

                    val cancelBtn: Button = dialogEventV.findViewById(R.id.cancel_dialog_event)
                    cancelBtn.setOnClickListener() {
                        ad.dismiss()
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
