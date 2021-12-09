package com.example.smartnote.ui.discount

class Discount {
    lateinit var brand: String
    lateinit var thing: String
    var discount: Double = 0.0
    var truePrice: Double = 0.0
    var economy: Double = 0.0
    var percentage: Double = 0.0

    fun calculateData() {
        this.percentage = 100 - (discount / (truePrice / 100))
        this.economy = truePrice - discount
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Brend" + " : " + brand + "\n")
            .append("Thing" + " : " + thing + "\n")
            .append("Discount" + " : " + discount.toInt() + "\n")
            .append("True price" + " : " + truePrice.toInt() + "\n")
            .append("Economy" + " : " + economy.toInt() + "\n")
            .append("Percentage" + " : " + percentage.toInt() + " %" + "\n" + "\n")
        return builder.toString()
    }
}