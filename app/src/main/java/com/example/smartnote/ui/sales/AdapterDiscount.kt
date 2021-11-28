package com.example.smartnote.ui.sales

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.LanguageSupportUtils
import com.example.smartnote.R

class DiscountAdapter(private val items: ArrayList<Discount> ):
        RecyclerView.Adapter<DiscountAdapter.ViewHolder>() {
    lateinit var context: Context
    constructor(items: ArrayList<Discount>, context: Context?): this(items) {

        if (context != null ) {
            this.context = context
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val brend_tv: TextView
        val thing_tv: TextView
        val discount_tv: TextView
        val true_price_tv: TextView
        val economy_tv: TextView
        val percentage_tv: TextView

        init {
            brend_tv = view.findViewById(R.id.brend_tv)
            thing_tv = view.findViewById(R.id.thing_name_tv)
            discount_tv = view.findViewById(R.id.sales_tv)
            true_price_tv= view.findViewById(R.id.true_price_tv)
            economy_tv = view.findViewById(R.id.economy_tv)
            percentage_tv = view.findViewById(R.id.percenage_tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_discount, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DiscountAdapter.ViewHolder, position: Int) {
        holder.run {
            brend_tv.setText(items[position].brand)
            thing_tv.setText(items[position].thing)
            discount_tv.setText(items[position].discount.toInt().toString())
            true_price_tv.setText(items[position].true_price.toInt().toString())
            economy_tv.setText(items[position].economy.toInt().toString())
            percentage_tv.setText(items[position].percentage.toInt().toString() + "%")


            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(holder.brend_tv.context)

                builder.setMessage(context.getString(R.string.delete_the_item))

                builder.setPositiveButton(R.string.yes) { dialog, which ->
                    items.remove(items[position])
                    notifyDataSetChanged()

                }
                builder.setNegativeButton(R.string.no) { dialog, which ->

                }

                builder.setNeutralButton(R.string.share) { dialog, which ->
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val shareBody = LanguageSupportUtils.castToLangSales(context, items[position].toString())
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
