package com.example.smartnote

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.example.smartnote.ui.bank_account.Card
import com.example.smartnote.ui.credentials.Credentials
import com.example.smartnote.ui.sales.Sale
import com.example.smartnote.ui.shopping.ShoppingItem
import com.example.smartnote.ui.text_note.CardNote
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object DataStoreHandler {
    val SP_SHOPING_ITEMS_KEY = "SP_SHOPING_ITEMS"
    val SP_CARDS_KEY = "SP_CARDS"
    val SP_CREDENTIALS_KEY = "SP_CREDENTIALS"
    val SP_SALES_KEY = "SP_SALES"
    val SP_NOTES_KEY = "SP_NOTES"
    lateinit var shopingItems: ArrayList<ShoppingItem>
    lateinit var cards: ArrayList<Card>
    lateinit var credentials: ArrayList<Credentials>
    lateinit var sales: ArrayList<Sale>
    lateinit var notes: ArrayList<CardNote>

    init {
        shopingItems = getArrayList()
        cards = getArrayListCards()
        credentials =  getArrayListCredentials()
        sales = getArrayListSales()
        notes =  getArrayListNotes()
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
    fun saveArrayListCards() {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(cards)
        editor.putString(SP_CARDS_KEY, json)
        editor.apply()
    }

    fun getArrayListCards(): ArrayList<Card> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val gson = Gson()
        val json: String? = prefs.getString(SP_CARDS_KEY, null)
        val type: Type = object : TypeToken<ArrayList<Card?>?>() {}.getType()
        if (json != null) {
            return gson.fromJson(json, type)
        }
        return ArrayList()
    }
    fun saveArrayListCredentials() {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(credentials)
        editor.putString(SP_CREDENTIALS_KEY, json)
        editor.apply()
    }

    fun getArrayListCredentials(): ArrayList<Credentials> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val gson = Gson()
        val json: String? = prefs.getString(SP_CREDENTIALS_KEY, null)
        val type: Type = object : TypeToken<ArrayList<Credentials?>?>() {}.getType()
        if (json != null) {
            return gson.fromJson(json, type)
        }
        return ArrayList()
    }
    fun saveArrayListSales() {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(sales)
        editor.putString(SP_SALES_KEY, json)
        editor.apply()
    }

    fun getArrayListSales(): ArrayList<Sale> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val gson = Gson()
        val json: String? = prefs.getString(SP_SALES_KEY, null)
        val type: Type = object : TypeToken<ArrayList<Sale?>?>() {}.getType()
        if (json != null) {
            return gson.fromJson(json, type)
        }
        return ArrayList()
    }
    fun saveArrayListNotes() {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(notes)
        editor.putString(SP_NOTES_KEY, json)
        editor.apply()
    }

    fun getArrayListNotes(): ArrayList<CardNote> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val gson = Gson()
        val json: String? = prefs.getString(SP_NOTES_KEY, null)
        val type: Type = object : TypeToken<ArrayList<CardNote?>?>() {}.getType()
        if (json != null) {
            return gson.fromJson(json, type)
        }
        return ArrayList()
    }
}