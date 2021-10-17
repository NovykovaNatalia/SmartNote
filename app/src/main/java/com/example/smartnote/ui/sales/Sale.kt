package com.example.smartnote.ui.sales

class Sale {
    lateinit var brand: String
    lateinit var thing: String
    var sale: Double = 0.0
    var true_price: Double = 0.0
    var economy: Double = 0.0
    var percentage: Double = 0.0

    fun calculateData() {
        this.percentage = 100 - (sale / (true_price / 100))
        this.economy = true_price - sale
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Brand" + " : " + brand + "\n")
            .append("Thing" + " : " + thing + "\n")
            .append("Sale" + " : " + sale.toInt() + "\n")
            .append("True price" + " : " + true_price.toInt() + "\n")
            .append("Economy" + " : " + economy.toInt() + "\n")
            .append("Economy percentage" + " : " + percentage.toInt() + " %" + "\n" + "\n")
        return builder.toString()
    }
}