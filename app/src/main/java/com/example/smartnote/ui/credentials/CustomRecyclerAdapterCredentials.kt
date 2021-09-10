package com.example.smartnote.ui.credentials

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.R
import com.example.smartnote.ui.bank_account.Card

class CustomAdapterCredentials(private val items: ArrayList<Credentials> ):
        RecyclerView.Adapter<CustomAdapterCredentials.ViewHolder>() {
    lateinit var context: Context;

    constructor(items: ArrayList<Credentials>, context: Context?): this(items) {

        if (context != null ) {
            this.context = context
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val credentials_tv: TextView
        val reference_tv: TextView

        init {
            credentials_tv = view.findViewById(R.id.name_credentials_tv)
            reference_tv = view.findViewById(R.id.reference_credential_tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapterCredentials.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_credentials, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomAdapterCredentials.ViewHolder, position: Int) {
        holder.run {
            credentials_tv.setText(items[position].credential)
            reference_tv.setText(items[position].reference)


            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(holder.credentials_tv.context)

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
                    val shareBody =  items[position].toString()
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
    
}