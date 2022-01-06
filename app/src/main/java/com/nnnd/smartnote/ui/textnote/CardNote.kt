package com.nnnd.smartnote.ui.textnote

class CardNote {
    lateinit var title: String
    lateinit var note: String

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Title" + " : " + title  + "\n")
                .append("Note" + " : " + note + "\n" + "\n")

        return builder.toString()
    }
}