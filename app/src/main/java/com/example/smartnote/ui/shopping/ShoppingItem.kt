package com.example.smartnote.ui.shopping

class ShoppingItem {
    lateinit var itemname: String
    lateinit var count: String
    var quantity: Int = 1


    override fun toString(): String {
        val builder = StringBuilder()
        builder.append(itemname + " : " + quantity  + "\n")
        return builder.toString()
    }
}