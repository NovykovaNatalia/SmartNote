package com.nnnd.smartnote

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.nnnd.smartnote.ui.bankaccount.Card
import com.nnnd.smartnote.ui.credentials.Credentials
import com.nnnd.smartnote.ui.events.Event
import com.nnnd.smartnote.ui.discount.Discount
import com.nnnd.smartnote.ui.shopping.ShoppingItem
import com.nnnd.smartnote.ui.textnote.CardNote
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nnnd.smartnote.ui.draws.CardDraw
import com.nnnd.smartnote.ui.draws.PaintItem
import java.lang.reflect.Type

object DataStoreHandler {
    val SP_SHOPING_ITEMS_KEY = "SP_SHOPING_ITEMS"
    val SP_CARDS_KEY = "SP_CARDS"
    val SP_CREDENTIALS_KEY = "SP_CREDENTIALS"
    val SP_SALES_KEY = "SP_SALES"
    val SP_NOTES_KEY = "SP_NOTES"
//    val SP_DRAW_KEY = "SP_DRAW"
    val SP_DRAWS_KEY = "SP_DRAWS"
    val SP_EVENTS_KEY = "SP_EVENTS"
    val SP_CURRENT_LANGUAGE_KEY: String? = "SP_CURRENT_LANGUAGE"

    var shoppingItems: ArrayList<ShoppingItem>
    var cards: ArrayList<Card>
    var credentials: ArrayList<Credentials>
    var discounts: ArrayList<Discount>
    var notes: ArrayList<CardNote>
    var events: ArrayList<Event>
    var draws: ArrayList<PaintItem>
//    var draw: ArrayList<CardDraw>
    var currentLanguage: String = "en"

    init {
        shoppingItems = getShoppings()
        cards = getArrayListCards()
        credentials =  getArrayListCredentials()
        discounts = getArrayListSales()
        notes =  getArrayListNotes()
        events = getArrayListEvents()
        currentLanguage = getCurrentLang()
//        draw = getArrayListDraw()
        draws = getArrayListDraws()
    }

    fun saveArrayListEvents() {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(events)
        editor.putString(SP_EVENTS_KEY, json)
        editor.apply()
    }

    fun getArrayListEvents(): ArrayList<Event> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val gson = Gson()
        val json: String? = prefs.getString(SP_EVENTS_KEY, null)
        val type: Type = object : TypeToken<ArrayList<Event?>?>() {}.getType()
        if (json != null) {
            return gson.fromJson(json, type)
        }
        return ArrayList()
    }

    fun saveShoppings() {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(shoppingItems)
        editor.putString(SP_SHOPING_ITEMS_KEY, json)
        editor.apply()
    }

    fun  getShoppings(): ArrayList<ShoppingItem> {
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
        val json: String = gson.toJson(discounts)
        editor.putString(SP_SALES_KEY, json)
        editor.apply()
    }

    fun getArrayListSales(): ArrayList<Discount> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val gson = Gson()
        val json: String? = prefs.getString(SP_SALES_KEY, null)
        val type: Type = object : TypeToken<ArrayList<Discount?>?>() {}.getType()
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

//    fun saveArrayListDraw() {
//            val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
//            val editor: SharedPreferences.Editor = prefs.edit()
//            val gson = Gson()
//            val json: String = gson.toJson(draw)
//            editor.putString(SP_NOTES_KEY, json)
//            editor.apply()
//    }

//    fun getArrayListDraw(): ArrayList<CardDraw> {
//        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
//        val gson = Gson()
//        val json: String? = prefs.getString(SP_DRAW_KEY, null)
//        val type: Type = object : TypeToken<ArrayList<CardDraw?>?>() {}.getType()
//        if (json != null) {
//            return gson.fromJson(json, type)
//        }
//        return ArrayList()
//    }

    fun saveArrayListDraws() {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(draws)
        editor.putString(SP_DRAWS_KEY, json)
        editor.apply()
    }

    fun getArrayListDraws(): ArrayList<PaintItem> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val gson = Gson()
        val json: String? = prefs.getString(SP_DRAWS_KEY, null)
        val type: Type = object : TypeToken<ArrayList<PaintItem?>?>() {}.getType()
        if (json != null) {
            return gson.fromJson(json, type)
        }
        return ArrayList()
    }

    fun getCurrentLang(): String {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        var result = prefs.getString(SP_CURRENT_LANGUAGE_KEY, "en")
        if(result != null) {
            return result;
        }
        return "en";
    }

    fun saveCurrentLang() {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext())
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(SP_CURRENT_LANGUAGE_KEY, currentLanguage)
        editor.apply()
    }
}