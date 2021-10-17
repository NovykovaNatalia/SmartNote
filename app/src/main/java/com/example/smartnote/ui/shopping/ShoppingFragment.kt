package com.example.smartnote.ui.shopping

import CustomAdapterShopping
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
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
import com.example.smartnote.R
import java.util.*
import kotlin.collections.ArrayList


class ShoppingFragment : Fragment() {
    lateinit var editText: EditText
    lateinit var recyclerViewShopping: RecyclerView
    lateinit var addBtn: Button
    lateinit var list: ArrayList<ShoppingItem>
    lateinit var customAdapterShopping: CustomAdapterShopping
    lateinit var btnSpeach: ImageView
    private  val REQUEST_CODE_STT = 1

    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        list = DataStoreHandler.shoppingItems

        val root = inflater.inflate(R.layout.fragment_shopping, container, false)
        recyclerViewShopping = root.findViewById(R.id.recyclerViewShopping)
        recyclerViewShopping.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        customAdapterShopping = CustomAdapterShopping(list, context)
        recyclerViewShopping.adapter = customAdapterShopping
        customAdapterShopping.notifyDataSetChanged()
        editText = root.findViewById(R.id.editText)
        btnSpeach = root.findViewById(R.id.btn_speach)

        addBtn = root.findViewById(R.id.btnAdd)


        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val atb = AnimationUtils.loadAnimation(context, R.anim.atb)
        val btt = AnimationUtils.loadAnimation(context, R.anim.btt)
        val btn = AnimationUtils.loadAnimation(context, R.anim.btn)

        btnSpeach.startAnimation(ttb)
        addBtn.startAnimation(ttb)
        editText.startAnimation(ttb)
        recyclerViewShopping.startAnimation(ttb)
        btnSpeach.setOnClickListener{
            speak()
        }

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

    private fun speak() {
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi, speak something")

        try {
            startActivityForResult(mIntent, REQUEST_CODE_STT)
            Log.e("natl", "input")
        } catch (e: Exception) {
            Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            Log.e("natl", "exception")
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // Handle the result for our request code.
            REQUEST_CODE_STT -> {
                // Safety checks to ensure data is available.
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Retrieve the result array.
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // Ensure result array is not null or empty to avoid errors.
                    if (!result.isNullOrEmpty()) {
                        // Recognized text is in the first position.
                        val recognizedText = result[0]
                        // Do what you want with the recognized text.
                        editText .setText(recognizedText)
                    }
                }
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}