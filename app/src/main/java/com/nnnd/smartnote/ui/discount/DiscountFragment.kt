package com.nnnd.smartnote.ui.discount

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nnnd.smartnote.DataStoreHandler
import com.nnnd.smartnote.LanguageSupportUtils
import com.nnnd.smartnote.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DiscountFragment : Fragment() {
    lateinit var brend: EditText
    lateinit var thing: EditText
    lateinit var discount: EditText
    lateinit var truePrice: EditText
    lateinit var discountList: ArrayList<Discount>
    lateinit var discountAdapter: DiscountAdapter

    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        discountList = DataStoreHandler.discounts
        val view = inflater.inflate(R.layout.fragment_discounts, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)

        var recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewSales)
        recyclerView.startAnimation(ttb)
        val sv: SearchView = view.findViewById(R.id.searchViewSales)
        sv.startAnimation(ttb)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        discountAdapter = DiscountAdapter(discountList, context)
        recyclerView.adapter = discountAdapter
        discountAdapter.notifyDataSetChanged()

        val fab: FloatingActionButton = view.findViewById(R.id.floating_btn_sales)
        fab.startAnimation(ttb)
        fab.setOnClickListener {
            val mDialogViewSales = LayoutInflater.from(context).inflate(R.layout.discount_dialog, null);
            val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogViewSales)
                    .setTitle(R.string.discount)
            val mAlertDialog = mBuilder.show()

            brend = mDialogViewSales.findViewById(R.id.brend)
            thing = mDialogViewSales.findViewById(R.id.thing_name)
            discount = mDialogViewSales.findViewById(R.id.discount)
            truePrice = mDialogViewSales.findViewById(R.id.true_price)
            val cancelBtn: Button = mDialogViewSales.findViewById(R.id.cancel_discount)
            val saveBtn: Button = mDialogViewSales.findViewById<Button>(R.id.save_discount)

            saveBtn.setOnClickListener {
                mAlertDialog.dismiss()
                if (brend.text.isNotEmpty() && thing.text.isNotEmpty() && discount.text.isNotEmpty() && truePrice.text.isNotEmpty()) {
                    var cardSales = Discount()
                    cardSales.brand = brend.text.toString()
                    cardSales.thing = thing.text.toString()
                    cardSales.discount = discount.text.toString().toDouble()
                    cardSales.truePrice = truePrice.text.toString().toDouble()

                    cardSales.calculateData()

                    discountList.add(cardSales)
                } else {
                    Toast.makeText(context, R.string.put_values, Toast.LENGTH_LONG).show()
                }
                discountAdapter.notifyDataSetChanged()

            }
            cancelBtn.setOnClickListener() {
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
        menu.findItem(R.id.normal).setVisible(false)
        menu.findItem(R.id.emboss).setVisible(false)
        menu.findItem(R.id.blur).setVisible(false)
        menu.findItem(R.id.clear).setVisible(false)
        menu.findItem(R.id.delete_checked_list).setVisible(false)
        menu.findItem(R.id.rubber).setVisible(false)
        menu.findItem(R.id.add).setVisible(false)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == R.id.settings) {
            return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
        }
        if (id == R.id.share) {
            val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
            when (currentFragment) {
                is DiscountFragment -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    var shareStr = DataStoreHandler.discounts.toString()
                    shareStr = shareStr.replace('[', ' ')
                    shareStr = shareStr.replace(']', ' ')
                    shareStr = shareStr.replace(",", "")
                    shareStr = context?.let { LanguageSupportUtils.castToLangSales(it, shareStr) }!!
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareStr)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareStr)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                }
            }
            return true
        }
        if (id == R.id.delete) {
            val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
            when (currentFragment) {
                is DiscountFragment -> {
                    val dialogView = layoutInflater.inflate(R.layout.delete_list_layout, null);
                    val builder = AlertDialog.Builder(context)
                            .setView(dialogView)
                            .setTitle(context?.getString(R.string.delete_the_list))
                    val deleteListAd = builder.show()

                    val noBtn : TextView = dialogView.findViewById(R.id.noBtn)
                    val yesBtn : TextView = dialogView.findViewById(R.id.yesBtn)
                    noBtn.setOnClickListener{
                        deleteListAd.dismiss()
                    }
                    yesBtn.setOnClickListener {
                        if(!DataStoreHandler.discounts.isEmpty()){
                            DataStoreHandler.discounts.removeAll(DataStoreHandler.discounts)
                            currentFragment.discountAdapter.notifyDataSetChanged()
                            DataStoreHandler.saveShoppings()
                            deleteListAd.dismiss()
                        } else {
                            Toast.makeText(context, getString(R.string.list_is_empty), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}