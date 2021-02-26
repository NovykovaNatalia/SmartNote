package com.example.smartnote2.ui.bank_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.smartnote2.R

class BankAccountFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_bank_account, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        return root
    }
}