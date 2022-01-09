package com.nnnd.smartnote.ui.shopping

import java.util.*

class ShoppingItem {
    lateinit var itemname: String
    var quantity: Int = 1
    var isFilled = false
    var date = Date()

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append(itemname + " : " + quantity + "\n")
        return builder.toString()
    }
}