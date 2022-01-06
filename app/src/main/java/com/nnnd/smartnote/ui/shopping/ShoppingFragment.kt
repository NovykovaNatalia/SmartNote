package com.nnnd.smartnote.ui.shopping

import ShoppingAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nnnd.smartnote.DataStoreHandler
import com.nnnd.smartnote.R
import java.util.*
import kotlin.collections.ArrayList


class ShoppingFragment : Fragment() {
    lateinit var editText: EditText
    lateinit var recyclerViewShopping: RecyclerView
    lateinit var addBtn: ImageButton
    lateinit var list: ArrayList<ShoppingItem>
    lateinit var shoppingAdapter: ShoppingAdapter
    lateinit var btnSpeach: ImageView
    private val REQUEST_CODE_STT = 1

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
        shoppingAdapter = ShoppingAdapter(list, context)
        recyclerViewShopping.adapter = shoppingAdapter
        shoppingAdapter.notifyDataSetChanged()
        editText = root.findViewById(R.id.editText)
        btnSpeach = root.findViewById(R.id.btn_speach)

        addBtn = root.findViewById(R.id.btnAdd)


        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val atb = AnimationUtils.loadAnimation(context, R.anim.atb)

        btnSpeach.startAnimation(ttb)
        addBtn.startAnimation(ttb)
        editText.startAnimation(ttb)
        recyclerViewShopping.startAnimation(ttb)
        btnSpeach.setOnClickListener {
            speak()
        }

        addBtn.setOnClickListener {
            addBtn.startAnimation(atb)
            if (editText.length() > 0) {
                var shoppingItem = ShoppingItem()
                shoppingItem.itemname = editText.text.toString()
                list.add(shoppingItem)
            }
            editText.setText("")
            shoppingAdapter.notifyDataSetChanged()
        }
        return root
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
            return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
        }
        if (id == R.id.share) {
            val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
            when (currentFragment) {
                is ShoppingFragment -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    var sharStr = DataStoreHandler.shoppingItems.toString()
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
                is ShoppingFragment -> {
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
                        if(!DataStoreHandler.shoppingItems.isEmpty()){
                            DataStoreHandler.shoppingItems.removeAll(DataStoreHandler.shoppingItems)
                            currentFragment.shoppingAdapter.notifyDataSetChanged()
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
        if (id == R.id.delete_checked_list) {
            val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
            when (currentFragment) {
                is ShoppingFragment -> {
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
                        if(!DataStoreHandler.shoppingItems.isEmpty()){
                            val filteredList = DataStoreHandler.shoppingItems
                                    .filter { !it.isFilled }
                            DataStoreHandler.shoppingItems.removeAll(DataStoreHandler.shoppingItems)
                            DataStoreHandler.shoppingItems.addAll(filteredList)

                            currentFragment.shoppingAdapter.notifyDataSetChanged()
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
            REQUEST_CODE_STT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if (!result.isNullOrEmpty()) {
                        val recognizedText = result[0]
                        editText.setText(recognizedText)
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}