package com.nnnd.smartnote.ui.draws

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nnnd.smartnote.DataStoreHandler
import com.nnnd.smartnote.LanguageSupportUtils
import com.nnnd.smartnote.R
import com.nnnd.smartnote.ui.textnote.TextNoteAdapter
import java.util.*

class DrawFragmentFirst : Fragment() {
    lateinit var noteEt: EditText
    lateinit var titleEt : EditText
    lateinit var recyclerView: RecyclerView
    lateinit var cardDraw:ArrayList<CardDraw>
    lateinit var textDrawAdapter: DrawAdapter
    private val  REQUEST_CODE_SPEECH = 100

    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cardDraw = DataStoreHandler.draw
        val view = inflater.inflate(R.layout.fragment_draw, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewDraw)
        recyclerView.startAnimation(ttb)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        textDrawAdapter = DrawAdapter(cardDraw, context)
        recyclerView.adapter = textDrawAdapter
        textDrawAdapter.notifyDataSetChanged()
        val sv : SearchView = view.findViewById(R.id.searchViewDraw)
        sv.startAnimation(ttb)
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                var filteredList: ArrayList<CardDraw> = filter(cardDraw, newText)
                recyclerView.adapter = DrawAdapter(filteredList, context)
                (recyclerView.adapter as DrawAdapter).notifyDataSetChanged()
                return true
            }

        })

        val fab_sec : FloatingActionButton = view.findViewById(R.id.floating_btn_second_activity)
        fab_sec.startAnimation(ttb)

        fab_sec.setOnClickListener{
            val a = Intent(activity, SecondActivity::class.java)
            startActivity(a)

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
        menu.findItem(R.id.rubber).setVisible(false)
        menu.findItem(R.id.delete_checked_list).setVisible(false)
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
                is DrawFragmentFirst -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    var sharStr = DataStoreHandler.notes.toString()
                    sharStr = sharStr.replace('[', ' ')
                    sharStr = sharStr.replace(']', ' ')
                    sharStr = sharStr.replace(",", "")
                    sharStr = context?.let { LanguageSupportUtils.castToLangNotes(it, sharStr) }!!
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, sharStr)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, sharStr)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                }
            }
            return true
        }
        if (id == R.id.delete) {
            val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
            when (currentFragment) {
                is DrawFragmentFirst -> {
                    val dialogView = layoutInflater.inflate(R.layout.delete_list_layout, null);
                    val builder = AlertDialog.Builder(context)
                            .setView(dialogView)
                            .setTitle(context?.getString(R.string.delete_the_list))
                    val deleteListAd = builder.show()

                    val noBtn: TextView = dialogView.findViewById(R.id.noBtn)
                    val yesBtn: TextView = dialogView.findViewById(R.id.yesBtn)
                    noBtn.setOnClickListener {
                        deleteListAd.dismiss()
                    }
                    yesBtn.setOnClickListener {
                        if (!DataStoreHandler.notes.isEmpty()) {
                            DataStoreHandler.notes.removeAll(DataStoreHandler.notes)
                            currentFragment.textDrawAdapter.notifyDataSetChanged()
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
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi, speak something")

        try{
            startActivityForResult(mIntent, REQUEST_CODE_SPEECH)
        }
        catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SPEECH -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if (!result.isNullOrEmpty()) {
                        val recognizedText = result[0]
                        noteEt.setText(recognizedText)
                    }
                }
            }
        }
    }

    private fun filter(cardList: ArrayList<CardDraw>, query: String): ArrayList<CardDraw> {
        var resultList:ArrayList<CardDraw> = java.util.ArrayList()

        for (cardDraw in cardList) {
            if(cardDraw.note.contains(query.toLowerCase()) && cardDraw.title.contains(query.toLowerCase())) {
                resultList.add(cardDraw)
            }
        }
        return resultList
    }
}


