package com.example.smartnote.ui.credentials

class Credentials {
    lateinit var credential: String
    lateinit var reference: String

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Credentials" + " : " + credential + "\n")
            .append("Reference" + " : " + reference + "\n")
        return builder.toString()
    }
}