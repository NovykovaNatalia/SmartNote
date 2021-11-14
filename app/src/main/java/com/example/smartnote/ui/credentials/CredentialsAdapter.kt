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

class CredentialsAdapter(private val items: ArrayList<Credentials> ) :
        RecyclerView.Adapter<CredentialsAdapter.ViewHolder>() {
    lateinit var context: Context

    constructor(items: ArrayList<Credentials>, context: Context?): this(items) {
        if (context != null ) {
            this.context = context
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val credentialsTv: TextView
        val referenceTv: TextView

        init {
            credentialsTv = view.findViewById(R.id.name_credentials_tv)
            referenceTv = view.findViewById(R.id.reference_credential_tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CredentialsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_credentials, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CredentialsAdapter.ViewHolder, position: Int) {
        holder.run {
            credentialsTv.setText(items[position].credential)
            referenceTv.setText(items[position].reference)

            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(holder.credentialsTv.context)
                builder.setMessage(context.getString(R.string.delete_credentials))
                builder.setPositiveButton(context.getString(R.string.yes)) { dialog, which ->
                    items.remove(items[position])
                    notifyDataSetChanged()

                }

                builder.setNegativeButton(context.getString(R.string.no)) { dialog, which ->
                }

                builder.setNeutralButton(context.getString(R.string.share)) { dialog, which ->
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val shareBody =  items[position].toString()
                    val shareSub = "items[position]"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.choose_one)))
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
                notifyDataSetChanged()
            }
        }
    }
}