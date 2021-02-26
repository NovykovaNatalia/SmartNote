package com.example.smartnote2.ui.shopping

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.smartnote2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class ShoppingFragment : Fragment() {
    lateinit var editText: EditText
    lateinit var listView: ListView
    lateinit var fab: FloatingActionButton
    var list: ArrayList<String> = ArrayList()
    val SP_LIST_KEY = "SP_LIST_KEY"
    lateinit var arrayAdapter: ArrayAdapter<String>


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        list = getArrayList(SP_LIST_KEY)

        val root = inflater.inflate(R.layout.fragment_shopping, container, false)

        listView = root.findViewById(R.id.listView)
        editText = root.findViewById(R.id.editText)

        fab = root.findViewById(R.id.btnAdd)


        clearElements()

        arrayAdapter =
            context?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, list) }!!

        arrayAdapter.notifyDataSetChanged()
        listView.adapter = arrayAdapter

        fab.setOnClickListener {
            if(editText.length() > 0) {
            list.add(editText.text.toString())
            }
            editText.setText("")
            arrayAdapter.notifyDataSetChanged()
        }

        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveArrayList(list, SP_LIST_KEY)
    }

    fun saveArrayList(list: ArrayList<String>, key: String?) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getArrayList(key: String?): ArrayList<String> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val gson = Gson()
        val json: String? = prefs.getString(key, null)
        Log.e("dnovykov", "JSON FROM sp: " + json)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.getType()
        if (json != null) {
            return gson.fromJson(json, type)
        }
        return ArrayList()
    }

    fun clearElements() {
        listView.setOnItemClickListener { parent, view, position, id ->
            val builder = AlertDialog.Builder(context)

            builder.setMessage("Delete the god?")

            builder.setPositiveButton("YES"){dialog, which ->
                list.removeAt(position)
                 arrayAdapter.notifyDataSetChanged()
            }
            builder.setNegativeButton("No"){dialog,which ->

            }
            val dialog: AlertDialog = builder.create()

            dialog.show()
             }
        }
}