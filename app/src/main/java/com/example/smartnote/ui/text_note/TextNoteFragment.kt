package com.example.smartnote.ui.text_note

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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.DataStoreHandler
import com.example.smartnote.LanguageSupportUtils
import com.example.smartnote.R
import com.example.smartnote.ui.settings.SettingsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class TextNoteFragment : Fragment() {
    lateinit var note: EditText
    lateinit var note_tv: TextView
    lateinit var floatingActionButtonNote: FloatingActionButton
    lateinit var recyclerViewNote: RecyclerView
    lateinit var cardListNote:ArrayList<CardNote>
    lateinit var customAdapterNote: CustomAdapterNote
    lateinit var saveActionButtonNote: Button
    lateinit var cancelActionButtonNote: Button
    lateinit var searchViewNote: SearchView
    lateinit var image_voice: ImageView

    private val  REQUEST_CODE_SPEECH = 100

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cardListNote = DataStoreHandler.notes
        val view = inflater.inflate(R.layout.fragment_text_note, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        recyclerViewNote = view.findViewById<RecyclerView>(R.id.recyclerViewNote)
        recyclerViewNote.startAnimation(ttb)

        recyclerViewNote.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        customAdapterNote = CustomAdapterNote(cardListNote, context)
        recyclerViewNote.adapter = customAdapterNote
        customAdapterNote.notifyDataSetChanged()
        searchViewNote = view.findViewById(R.id.searchViewNote)
        searchViewNote.startAnimation(ttb)
        searchViewNote.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                var filteredList:ArrayList<CardNote> = filter(cardListNote, newText)
                recyclerViewNote.adapter = CustomAdapterNote(filteredList, context)
                (recyclerViewNote.adapter as CustomAdapterNote).notifyDataSetChanged()
                return true
            }

        })

        floatingActionButtonNote = view.findViewById(R.id.floating_btn_note)
        floatingActionButtonNote.startAnimation(ttb)
        floatingActionButtonNote.setOnClickListener{
            val mDialogViewNote = LayoutInflater.from( context).inflate(R.layout.note_dialog, null);
            val mItemViewNote = LayoutInflater.from(context).inflate(R.layout.item_note, null )

            val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogViewNote)
                    .setTitle(getString(R.string.notes))
            val mAlertDialog = mBuilder.show()

            note = mDialogViewNote.findViewById(R.id.note)
            note_tv = mItemViewNote.findViewById(R.id.note_tv)
            image_voice = mDialogViewNote.findViewById(R.id.image_voice)

            image_voice.setOnClickListener{
                speak()
            }

            cancelActionButtonNote = mDialogViewNote.findViewById(R.id.cancel_dialog_note)
            saveActionButtonNote = mDialogViewNote.findViewById<Button>(R.id.save_dialog_note);

            saveActionButtonNote.setOnClickListener {
                    mAlertDialog.dismiss()
                if(note.text.isNotEmpty()) {
                    var cardNote = CardNote()
                    cardNote.note = note.text.toString()

                    cardListNote.add(cardNote)
                } else {
                    Toast.makeText(context, "Put value", Toast.LENGTH_LONG).show()
                }
                customAdapterNote.notifyDataSetChanged()

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
                    DataStoreHandler.notes.removeAll(DataStoreHandler.notes)
                    currentFragment.customAdapterNote.notifyDataSetChanged()
                    DataStoreHandler.saveArrayListNotes()
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
            // Handle the result for our request code.
            REQUEST_CODE_SPEECH -> {
                // Safety checks to ensure data is available.
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Retrieve the result array.
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // Ensure result array is not null or empty to avoid errors.
                    if (!result.isNullOrEmpty()) {
                        // Recognized text is in the first position.
                        val recognizedText = result[0]
                        // Do what you want with the recognized text.
                        note.setText(recognizedText)
                    }
                }
            }
        }
    }

    private fun filter (cardList:ArrayList<CardNote>, query: String): ArrayList<CardNote> {
        var resultList:ArrayList<CardNote> = java.util.ArrayList()

        for (cardNote in cardList) {
            if(cardNote.note.contains(query.toLowerCase())) {
                resultList.add(cardNote)
            }
        }
        return resultList
    }
}


