package com.nnnd.smartnote.ui.draws

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
import com.nnnd.smartnote.ui.textnote.CardNote

class DrawAdapter(private val items: ArrayList<CardDraw>) :
        RecyclerView.Adapter<DrawAdapter.ViewHolder>() {

    lateinit var context: Context;

    constructor(items: ArrayList<CardDraw>, context: Context?) : this(items) {

        if (context != null) {
            this.context = context
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title_tv : TextView
        val note_tv: TextView
        val ll: LinearLayout
        init {
            title_tv = view.findViewById(R.id.title_tv)
            note_tv = view.findViewById(R.id.note_tv)
            ll = view.findViewById(R.id.dots3_ll_id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DrawAdapter.ViewHolder, position: Int) {
        holder.run {
            if(items[position].title != null) {
                title_tv.setText(items[position].title)
            }
            note_tv.setText(items[position].note)

            ll.setOnClickListener {
                val dialogView = LayoutInflater.from( context).inflate(R.layout.delete_share_layout, null);
                val builder = AlertDialog.Builder(context)
                        .setView(dialogView)
                        .setTitle(context.getString(R.string.delete_the_item))
                val alertDialog = builder.show()
                val imageShare : ImageView = dialogView.findViewById(R.id.shareIv)
                val noBtn : TextView = dialogView.findViewById(R.id.noBtn)
                val imageEdit : ImageView = dialogView.findViewById(R.id.editIv)
                val yesBtn : TextView = dialogView.findViewById(R.id.yesBtn)

                imageShare.setOnClickListener {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val shareBody =
                            LanguageSupportUtils.castToLangNotes(context, items[position].toString())
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.choose_one)))
                }

                imageEdit.setOnClickListener {
                    val noteDv = LayoutInflater.from(context)
                            .inflate(R.layout.note_dialog, null);

                    val builder = AlertDialog.Builder(context)
                            .setView(noteDv)
                            .setTitle(context.getString(R.string.note))
                    val ad = builder.show()

                    val dialogTitleEt: EditText = noteDv.findViewById(R.id.title_note)
                    val dialogNoteEt: EditText = noteDv.findViewById(R.id.note)
                    if(items[position].title != null) {
                        dialogNoteEt.setText(items[position].title)
                    }
                    dialogNoteEt.setText(items[position].note)

                    val saveBtn: Button = noteDv.findViewById(R.id.save_dialog_note)
                    saveBtn.setOnClickListener {
                        ad.dismiss()
                        if(dialogNoteEt.text.isNotEmpty() && dialogTitleEt.text.isNotEmpty()) {
                            items[position].note = dialogNoteEt.text.toString()
                            items[position].title = dialogNoteEt.text.toString()
                        } else {
                            Toast.makeText(context, "Put value!", Toast.LENGTH_LONG).show()
                        }
                        notifyDataSetChanged()
                    }
                    alertDialog.dismiss()
                    val  cancelBtn: Button = noteDv.findViewById(R.id.cancel_dialog_note)
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