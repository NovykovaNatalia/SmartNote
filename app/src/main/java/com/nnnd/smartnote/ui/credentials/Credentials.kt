package com.nnnd.smartnote.ui.credentials

class Credentials {
    lateinit var credential: String
    lateinit var reference: String

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Credential" + " : " + credential + "\n")
            .append("Reference" + " : " + reference + "\n" + "\n")
        return builder.toString()
    }
}