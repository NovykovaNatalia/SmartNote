package com.nnnd.smartnote.ui.credentials

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.nnnd.smartnote.LanguageSupportUtils
import com.nnnd.smartnote.R

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
        val ll: LinearLayout
        init {
            credentialsTv = view.findViewById(R.id.name_credentials_tv)
            referenceTv = view.findViewById(R.id.reference_credential_tv)
            ll = view.findViewById(R.id.line_three)
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
                        LanguageSupportUtils.castToLangCredentials(context, items[position].toString())
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.choose_one)))
                }

                imageEdit.setOnClickListener {
                    val credentialsDv = LayoutInflater.from(context)
                            .inflate(R.layout.credentials_dialog, null);

                    val builder = AlertDialog.Builder(context)
                            .setView(credentialsDv)
                            .setTitle(context.getString(R.string.credentials))
                    val ad = builder.show()

                    val dialogCredentialsEt: EditText = credentialsDv.findViewById(R.id.credential)
                    dialogCredentialsEt.setText(items[position].credential)
                    val dialogReferenceEt: EditText = credentialsDv.findViewById(R.id.reference)
                    dialogReferenceEt.setText(items[position].reference)

                    val saveBtn: Button = credentialsDv.findViewById(R.id.save_dialog_credentials)
                    saveBtn.setOnClickListener {
                        ad.dismiss()
                        if(dialogCredentialsEt.text.isNotEmpty() && dialogReferenceEt.text.isNotEmpty()) {
                            items[position].credential = dialogCredentialsEt.text.toString()
                            items[position].reference = dialogReferenceEt.text.toString()

                        } else {
                            Toast.makeText(context, context.getString(R.string.put_values), Toast.LENGTH_LONG).show()
                        }
                        notifyDataSetChanged()
                    }
                    alertDialog.dismiss()
                    val  cancelBtn: Button = credentialsDv.findViewById(R.id.cancel_dialog_credentials)
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