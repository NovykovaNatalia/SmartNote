package com.example.smartnote.ui.credentials

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.R

class CustomAdapterCredentials(private val items: ArrayList<CardCredentials> ):
        RecyclerView.Adapter<CustomAdapterCredentials.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val credentials_tv: TextView
        val reference_tv: TextView

        init {
            // Define click listener for the ViewHolder's View.
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
                val dialog: AlertDialog = builder.create()

                dialog.show()
                notifyDataSetChanged()
            }

        }

    }
    
}