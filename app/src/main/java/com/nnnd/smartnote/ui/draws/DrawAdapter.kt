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
import java.text.DateFormat

class DrawAdapter(private val items: ArrayList<PaintItem>) :
        RecyclerView.Adapter<DrawAdapter.ViewHolder>() {

    lateinit var context: Context;

    constructor(items: ArrayList<PaintItem>, context: Context?) : this(items) {

        if (context != null) {
            this.context = context
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title_tv : TextView
        val date_tv: TextView
        val ll: LinearLayout
        init {
            title_tv = view.findViewById(R.id.title_tv)
            date_tv = view.findViewById(R.id.note_tv)
            ll = view.findViewById(R.id.dots3_ll_id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_draw, parent, false)
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
            val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
            date_tv.setText(dateFormatter.format(items[position].date))

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
                            LanguageSupportUtils.castToLangDraws(context, items[position].toString())
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.choose_one)))
                }

                imageEdit.setOnClickListener {
                    val drawDv = LayoutInflater.from(context)
                            .inflate(R.layout.draw_dialog, null);

                    val builder = AlertDialog.Builder(context)
                            .setView(drawDv)
                            .setTitle(context.getString(R.string.draw))
                    val ad = builder.show()

                    val dialogTitleEt: EditText = drawDv.findViewById(R.id.title_note)
                    if(items[position].title != null) {
                        dialogTitleEt.setText(items[position].title)
                    }

                    val saveBtn: Button = drawDv.findViewById(R.id.save_dialog_note)
                    saveBtn.setOnClickListener {
                        ad.dismiss()
                        if(dialogTitleEt.text.isNotEmpty()) {
                            items[position].title = dialogTitleEt.text.toString()
                        } else {
                            Toast.makeText(context, "Put value!", Toast.LENGTH_LONG).show()
                        }
                        notifyDataSetChanged()
                    }
                    alertDialog.dismiss()
                    val  cancelBtn: Button = drawDv.findViewById(R.id.cancel_dialog_note)
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