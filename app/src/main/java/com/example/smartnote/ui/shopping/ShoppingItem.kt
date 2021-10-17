package com.example.smartnote.ui.shopping

import java.util.*

class ShoppingItem {
    lateinit var itemname: String
    var quantity: Int = 1

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append(itemname + " : " + quantity + "\n")
        return builder.toString()
    }
}