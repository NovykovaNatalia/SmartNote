package com.example.smartnote.ui.shopping

import CustomAdapterShopping
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.DataStoreHandler
import com.example.smartnote.MyApplication
import com.example.smartnote.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class ShoppingFragment : Fragment() {
    lateinit var editText: EditText
    lateinit var recyclerViewShopping: RecyclerView
    lateinit var addBtn: Button
    lateinit var list: ArrayList<ShoppingItem>
    lateinit var customAdapterShopping: CustomAdapterShopping

    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        list = DataStoreHandler.shopingItems

        val root = inflater.inflate(R.layout.fragment_shopping, container, false)
        recyclerViewShopping = root.findViewById(R.id.recyclerViewShopping)
        recyclerViewShopping.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        customAdapterShopping = CustomAdapterShopping(list, context)
        recyclerViewShopping.adapter = customAdapterShopping
        customAdapterShopping.notifyDataSetChanged()
        editText = root.findViewById(R.id.editText)

        addBtn = root.findViewById(R.id.btnAdd)


        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val atb = AnimationUtils.loadAnimation(context, R.anim.atb)
        val btt = AnimationUtils.loadAnimation(context, R.anim.btt)
        val btn = AnimationUtils.loadAnimation(context, R.anim.btn)

        addBtn.startAnimation(ttb)
        editText.startAnimation(ttb)
        recyclerViewShopping.startAnimation(ttb)


        addBtn.setOnClickListener {
            addBtn.startAnimation(atb)
            //create shopingitem
            if(editText.length() > 0) {
                var shoppingItem = ShoppingItem()
                shoppingItem.itemname = editText.text.toString()
                list.add(shoppingItem)
            }
            editText.setText("")
            customAdapterShopping.notifyDataSetChanged()
        }
        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}