package com.example.smartnote.ui.sales

class Discount {
    lateinit var brand: String
    lateinit var thing: String
    var discount: Double = 0.0
    var true_price: Double = 0.0
    var economy: Double = 0.0
    var percentage: Double = 0.0

    fun calculateData() {
        this.percentage = 100 - (discount / (true_price / 100))
        this.economy = true_price - discount
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Brend" + " : " + brand + "\n")
            .append("Thing" + " : " + thing + "\n")
            .append("Discount" + " : " + discount.toInt() + "\n")
            .append("True price" + " : " + true_price.toInt() + "\n")
            .append("Economy" + " : " + economy.toInt() + "\n")
            .append("Percentage" + " : " + percentage.toInt() + " %" + "\n" + "\n")
        return builder.toString()
    }
}