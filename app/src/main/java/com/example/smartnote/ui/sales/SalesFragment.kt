package com.example.smartnote.ui.sales

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SalesFragment : Fragment() {

    lateinit var floatingActionButtonSalesFragment: FloatingActionButton
    lateinit var saveActionButtonSales: Button
    lateinit var cancelActionButtonSales: Button
    lateinit var searchViewSales: SearchView
    lateinit var brand: EditText
    lateinit var thing: EditText
    lateinit var sales: EditText
    lateinit var truePrice: EditText
    lateinit var brand_tv: TextView
    lateinit var thing_tv: TextView
    lateinit var sales_tv: TextView
    lateinit var true_price_tv: TextView
    lateinit var economy_tv: TextView
    lateinit var recyclerViewSales: RecyclerView
    var cardListSales:ArrayList<CardSales> = ArrayList()
    lateinit var customAdapterSales: CustomAdapterSales

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sales, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val atb = AnimationUtils.loadAnimation(context, R.anim.atb)
        val btt = AnimationUtils.loadAnimation(context, R.anim.btt)
        val btn = AnimationUtils.loadAnimation(context, R.anim.btn)

        recyclerViewSales = view.findViewById<RecyclerView>(R.id.recyclerViewSales)
        recyclerViewSales.startAnimation(ttb)
        searchViewSales = view.findViewById(R.id.searchViewSales)
        searchViewSales.startAnimation(ttb)

        recyclerViewSales.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        customAdapterSales = CustomAdapterSales(cardListSales)
        recyclerViewSales.adapter = customAdapterSales
        customAdapterSales.notifyDataSetChanged()

        floatingActionButtonSalesFragment = view.findViewById(R.id.floating_btn_sales)
        floatingActionButtonSalesFragment.startAnimation(ttb)
        floatingActionButtonSalesFragment.setOnClickListener{
            val mDialogViewSales = LayoutInflater.from( context).inflate(R.layout.sales_dialog, null);
            val mItemViewSales = LayoutInflater.from(context).inflate(R.layout.item_sales, null )

            val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogViewSales)
                    .setTitle("Sales")
            val mAlertDialog = mBuilder.show()

            brand = mDialogViewSales.findViewById(R.id.brand)
            thing = mDialogViewSales.findViewById(R.id.thing_name)
            sales = mDialogViewSales.findViewById(R.id.sales_price)
            truePrice = mDialogViewSales.findViewById(R.id.true_price)

            brand_tv = mItemViewSales.findViewById(R.id.brend_tv)
            thing_tv = mItemViewSales.findViewById(R.id.thing_name_tv)
            sales_tv = mItemViewSales.findViewById(R.id.sales_price_tv)
            true_price_tv = mItemViewSales.findViewById(R.id.true_price_tv)
            economy_tv = mItemViewSales.findViewById(R.id.economy_tv)


            cancelActionButtonSales = mDialogViewSales.findViewById(R.id.cancel_dialog_sales)
            saveActionButtonSales = mDialogViewSales.findViewById<Button>(R.id.save_dialog_sales);

            saveActionButtonSales.setOnClickListener {
                mAlertDialog.dismiss()
                var  cardSales = CardSales()
                cardSales.brand = brand.text.toString()
                cardSales.thing = thing.text.toString()
                cardSales.sale = sales.text.toString().toDouble()
                cardSales.true_price = truePrice.text.toString().toDouble()


                cardListSales.add(cardSales)
                println(cardListSales.toMutableList())
                println(cardListSales)
                customAdapterSales.notifyDataSetChanged()

            }
            cancelActionButtonSales.setOnClickListener() {
                mAlertDialog.dismiss()
            }

        }

        return view
    }

}