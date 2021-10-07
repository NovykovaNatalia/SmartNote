package com.example.smartnote.ui.text_note

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.DataStoreHandler
import com.example.smartnote.R
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_text_note, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val atb = AnimationUtils.loadAnimation(context, R.anim.atb)
        val btt = AnimationUtils.loadAnimation(context, R.anim.btt)
        val btn = AnimationUtils.loadAnimation(context, R.anim.btn)

        recyclerViewNote = view.findViewById<RecyclerView>(R.id.recyclerViewNote)
        recyclerViewNote.startAnimation(ttb)
        recyclerViewNote.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        customAdapterNote = CustomAdapterNote(cardListNote, context)
        recyclerViewNote.adapter = customAdapterNote
        customAdapterNote.notifyDataSetChanged()
        searchViewNote = view.findViewById(R.id.searchViewNote)
        searchViewNote.startAnimation(ttb)
        floatingActionButtonNote = view.findViewById(R.id.floating_btn_note)
        floatingActionButtonNote.startAnimation(ttb)
        floatingActionButtonNote.setOnClickListener{
            val mDialogViewNote = LayoutInflater.from( context).inflate(R.layout.note_dialog, null);
            val mItemViewNote = LayoutInflater.from(context).inflate(R.layout.item_note, null )

            val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogViewNote)
                    .setTitle("Notes")
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
}

