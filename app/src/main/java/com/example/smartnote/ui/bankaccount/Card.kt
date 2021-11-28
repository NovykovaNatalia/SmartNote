package com.example.smartnote.ui.bankaccount

import java.io.Serializable

class Card : Serializable{
    lateinit var account: String
    lateinit var bankName: String
    lateinit var accountNumber:String
    lateinit var nameSurname: String

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Account" + " : " + account + "\n")
                .append("Bank Name" + " : " + bankName + "\n")
                .append("Account Number" + " : " + accountNumber + "\n")
                .append("Name, Surname" + " : " + nameSurname + "\n" + "\n")
        return builder.toString()
    }
  }