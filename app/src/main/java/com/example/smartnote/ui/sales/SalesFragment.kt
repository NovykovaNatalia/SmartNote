package com.example.smartnote.ui.sales

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.DataStoreHandler
import com.example.smartnote.R
import com.example.smartnote.ui.bank_account.BankAccountFragment
import com.example.smartnote.ui.credentials.CredentialsFragment
import com.example.smartnote.ui.holiday.HolidayFragment
import com.example.smartnote.ui.settings.SettingsFragment
import com.example.smartnote.ui.shopping.ShoppingFragment
import com.example.smartnote.ui.text_note.TextNoteFragment
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
    lateinit var cardListSales:ArrayList<Sale>
    lateinit var customAdapterSales: CustomAdapterSales

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cardListSales = DataStoreHandler.sales
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
        customAdapterSales = CustomAdapterSales(cardListSales, context)
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
            saveActionButtonSales = mDialogViewSales.findViewById<Button>(R.id.save_dialog_sales)

            saveActionButtonSales.setOnClickListener {
                mAlertDialog.dismiss()
                if(brand.text.isNotEmpty() && thing.text.isNotEmpty() && sales.text.isNotEmpty() && truePrice.text.isNotEmpty()) {
                    var cardSales = Sale()
                    cardSales.brand = brand.text.toString()
                    cardSales.thing = thing.text.toString()
                    cardSales.sale = sales.text.toString().toDouble()
                    cardSales.true_price = truePrice.text.toString().toDouble()

                    cardSales.calculateData()

                    cardListSales.add(cardSales)
                } else {
                    Toast.makeText(context, "Put values!", Toast.LENGTH_LONG).show()
                }
                customAdapterSales.notifyDataSetChanged()

            }
            cancelActionButtonSales.setOnClickListener() {
                mAlertDialog.dismiss()
            }

        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.add).setVisible(false)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == R.id.settings) {
            val fragmentManager: FragmentManager = activity?.supportFragmentManager!!
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val setFragment = SettingsFragment()
            fragmentTransaction.replace(R.id.settings, setFragment)
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true
        }
        if (id == R.id.share) {
            val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
            when (currentFragment) {
                is SalesFragment -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    var sharStr = DataStoreHandler.sales.toString()
                    sharStr = sharStr.replace('[', ' ')
                    sharStr = sharStr.replace(']', ' ')
                    sharStr = sharStr.replace(",", "")
                    val shareBody = sharStr
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                }
            }
            return true
        }
        if (id == R.id.delete) {
            val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
            when (currentFragment) {
                is SalesFragment -> {
                    DataStoreHandler.sales.removeAll(DataStoreHandler.sales)
                    currentFragment.customAdapterSales.notifyDataSetChanged()
                    DataStoreHandler.saveArrayListSales()
                }
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}