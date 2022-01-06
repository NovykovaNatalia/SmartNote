package com.nnnd.smartnote.ui.textnote

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
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
import java.util.*
import kotlin.collections.ArrayList

class TextNoteFragment : Fragment() {
    lateinit var noteEt: EditText
    lateinit var titleEt : EditText
    lateinit var recyclerView: RecyclerView
    lateinit var cardListNote:ArrayList<CardNote>
    lateinit var textNoteAdapter: TextNoteAdapter
    private val  REQUEST_CODE_SPEECH = 100

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cardListNote = DataStoreHandler.notes
        val view = inflater.inflate(R.layout.fragment_text_note, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewNote)
        recyclerView.startAnimation(ttb)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        textNoteAdapter = TextNoteAdapter(cardListNote, context)
        recyclerView.adapter = textNoteAdapter
        textNoteAdapter.notifyDataSetChanged()
        val sv : SearchView = view.findViewById(R.id.searchViewNote)
        sv.startAnimation(ttb)
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                var filteredList:ArrayList<CardNote> = filter(cardListNote, newText)
                recyclerView.adapter = TextNoteAdapter(filteredList, context)
                (recyclerView.adapter as TextNoteAdapter).notifyDataSetChanged()
                return true
            }

        })

        val fab : FloatingActionButton = view.findViewById(R.id.floating_btn_note)
        fab.startAnimation(ttb)
        fab.setOnClickListener{
            val dialogViewNote = LayoutInflater.from( context).inflate(R.layout.note_dialog, null);
            val mBuilder = AlertDialog.Builder(context)
                    .setView(dialogViewNote)
                    .setTitle(getString(R.string.notes))
            val mAlertDialog = mBuilder.show()

            noteEt = dialogViewNote.findViewById(R.id.note)
            titleEt = dialogViewNote.findViewById(R.id.title_note)

            val cancelActionButtonNote : Button = dialogViewNote.findViewById(R.id.cancel_dialog_note)
            val saveActionButtonNote : Button = dialogViewNote.findViewById(R.id.save_dialog_note);

            saveActionButtonNote.setOnClickListener {
                    mAlertDialog.dismiss()
                if(noteEt.text.isNotEmpty() && titleEt.text.isNotEmpty()) {
                    var cardNote = CardNote()
                    cardNote.note = noteEt.text.toString()
                    cardNote.title = titleEt.text.toString()

                    cardListNote.add(cardNote)
                } else {
                    Toast.makeText(context, R.string.put_values, Toast.LENGTH_LONG).show()
                }
                textNoteAdapter.notifyDataSetChanged()
            }
            cancelActionButtonNote.setOnClickListener() {
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
            return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
        }
        if (id == R.id.share) {
            val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
            when (currentFragment) {
                is TextNoteFragment -> {
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
                is TextNoteFragment -> {
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
                        if(!DataStoreHandler.notes.isEmpty()){
                            DataStoreHandler.notes.removeAll(DataStoreHandler.notes)
                            currentFragment.textNoteAdapter.notifyDataSetChanged()
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

    private fun filter (cardList:ArrayList<CardNote>, query: String): ArrayList<CardNote> {
        var resultList:ArrayList<CardNote> = java.util.ArrayList()

        for (cardNote in cardList) {
            if(cardNote.note.contains(query.toLowerCase()) && cardNote.title.contains(query.toLowerCase())) {
                resultList.add(cardNote)
            }
        }
        return resultList
    }
}


