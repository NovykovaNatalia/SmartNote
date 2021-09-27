package com.example.smartnote.ui.sales

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

class CustomAdapterSales(private val items: ArrayList<Sale> ):
        RecyclerView.Adapter<CustomAdapterSales.ViewHolder>() {
    lateinit var context: Context;
    companion object{

        const val MY_CONSTANT = 100

    }
    constructor(items: ArrayList<Sale>, context: Context?): this(items) {

        if (context != null ) {
            this.context = context
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val brend_tv: TextView
        val thing_tv: TextView
        val sale_tv: TextView
        val true_price_tv: TextView
        val economy_tv: TextView
        val percentage_tv: TextView

        init {
            // Define click listener for the ViewHolder's View.
            brend_tv = view.findViewById(R.id.brend_tv)
            thing_tv = view.findViewById(R.id.thing_name_tv)
            sale_tv = view.findViewById(R.id.sales_price_tv)
            true_price_tv= view.findViewById(R.id.true_price_tv)
            economy_tv = view.findViewById(R.id.economy_tv)
            percentage_tv = view.findViewById(R.id.percenage_tv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapterSales.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sales, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomAdapterSales.ViewHolder, position: Int) {
        holder.run {
            brend_tv.setText(items[position].brand)
            thing_tv.setText(items[position].thing)
            sale_tv.setText(items[position].sale.toInt().toString())
            true_price_tv.setText(items[position].true_price.toInt().toString())
            economy_tv.setText(items[position].economy.toInt().toString())
            percentage_tv.setText(items[position].percentage.toInt().toString() + "%")


            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(holder.brend_tv.context)

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
