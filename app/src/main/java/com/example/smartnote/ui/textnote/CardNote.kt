package com.example.smartnote.ui.textnote

class CardNote {
    lateinit var note: String

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Note" + " : " + note + "\n" + "\n")

        return builder.toString()
    }
}