package com.example.smartnote2.ui.sales

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote2.R

class CustomAdapterSales(private val items: ArrayList<CardSales> ):
        RecyclerView.Adapter<CustomAdapterSales.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val brend_tv: TextView
        val thing_tv: TextView
        val sale_tv: TextView
        val true_price_tv: TextView
        val economy_tv: TextView
        init {
            // Define click listener for the ViewHolder's View.
            brend_tv = view.findViewById(R.id.brend_tv)
            thing_tv = view.findViewById(R.id.thing_name_tv)
            sale_tv = view.findViewById(R.id.sales_price_tv)
            true_price_tv= view.findViewById(R.id.true_price_tv)
            economy_tv = view.findViewById(R.id.economy_tv)
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
            sale_tv.setText(items[position].sale.toString())
            true_price_tv.setText(items[position].true_price.toString())
            economy_tv.setText((items[position].true_price - items[position].sale).toString())


            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(holder.brend_tv.context)

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