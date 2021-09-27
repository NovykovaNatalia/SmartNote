package com.example.smartnote.ui.text_note

class CardNote {
    lateinit var note: String

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Note" + " : " + note + "\n")

        return builder.toString()
    }
}