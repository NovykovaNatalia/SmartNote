package com.example.smartnote

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.example.smartnote.ui.shopping.ShoppingItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object DataStoreHandler {
    val SP_SHOPING_ITEMS_KEY = "SP_SHOPING_ITEMS"
    lateinit var shopingItems: ArrayList<ShoppingItem>

    init {
        shopingItems = getArrayList()
    }

    fun saveArrayList() {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(shopingItems)
        editor.putString(SP_SHOPING_ITEMS_KEY, json)
        editor.apply()
    }

    fun getArrayList(): ArrayList<ShoppingItem> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val gson = Gson()
        val json: String? = prefs.getString(SP_SHOPING_ITEMS_KEY, null)
        val type: Type = object : TypeToken<ArrayList<ShoppingItem?>?>() {}.getType()
        if (json != null) {
            return gson.fromJson(json, type)
        }
        return ArrayList()
    }
}