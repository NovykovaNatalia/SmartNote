package com.example.smartnote2.ui.text_note

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TextNoteFragment : Fragment() {
    lateinit var note: EditText
    lateinit var note_tv: TextView
    lateinit var floatingActionButtonNote: FloatingActionButton
    lateinit var recyclerViewNote: RecyclerView
    var cardListNote:ArrayList<CardNote> = ArrayList()
    lateinit var customAdapterNote: CustomAdapterNote
    lateinit var saveActionButtonNote: Button
    lateinit var cancelActionButtonNote: Button

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_text_note, container, false)
        recyclerViewNote = view.findViewById<RecyclerView>(R.id.recyclerViewNote)

        recyclerViewNote.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        customAdapterNote = CustomAdapterNote(cardListNote)
        recyclerViewNote.adapter = customAdapterNote
        customAdapterNote.notifyDataSetChanged()
        floatingActionButtonNote = view.findViewById(R.id.floating_btn_note)
        floatingActionButtonNote.setOnClickListener{
            val mDialogViewNote = LayoutInflater.from( context).inflate(R.layout.note_dialog, null);
            val mItemViewNote = LayoutInflater.from(context).inflate(R.layout.item_note, null )

            val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogViewNote)
                    .setTitle("Notes")
            val mAlertDialog = mBuilder.show()

            note = mDialogViewNote.findViewById(R.id.note)
            note_tv = mItemViewNote.findViewById(R.id.note_tv)

            cancelActionButtonNote = mDialogViewNote.findViewById(R.id.cancel_dialog_note)
            saveActionButtonNote = mDialogViewNote.findViewById<Button>(R.id.save_dialog_note);

            saveActionButtonNote.setOnClickListener {
                mAlertDialog.dismiss()
                var  cardNote = CardNote()
                cardNote.note = note.text.toString()

                cardListNote.add(cardNote)
                println(cardListNote.toMutableList())
                println(cardListNote)
                customAdapterNote.notifyDataSetChanged()

            }
            cancelActionButtonNote.setOnClickListener() {
                mAlertDialog.dismiss()
            }

        }

        return view
    }

}


