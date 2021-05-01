package com.example.smartnote2.ui.bank_account

import CustomAdapter
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text

class BankAccountFragment : Fragment() {
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var saveActionButton: Button
    lateinit var cancelActionButton: Button
    lateinit var salaryAccount: EditText
    lateinit var bankName: EditText
    lateinit var number: EditText
    lateinit var nameSurname: EditText
    lateinit var salaryAcountTV: TextView
    lateinit var bankNameTv: TextView
    lateinit var numberTV: TextView
    lateinit var nameSurnameTV: TextView
    lateinit var recyclerView: RecyclerView
    var cardList:ArrayList<Card> = ArrayList()
    lateinit var customAdapter: CustomAdapter
    lateinit var searchViewBankAccount: SearchView

    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_bank_account, container, false)

        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val atb = AnimationUtils.loadAnimation(context, R.anim.atb)
        val btt = AnimationUtils.loadAnimation(context, R.anim.btt)
        val btn = AnimationUtils.loadAnimation(context, R.anim.btn)

        recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        customAdapter = CustomAdapter(cardList)
        recyclerView.adapter = customAdapter
        customAdapter.notifyDataSetChanged()

        searchViewBankAccount = root.findViewById(R.id.searchView)
        searchViewBankAccount.startAnimation(ttb)
        floatingActionButton = root.findViewById(R.id.floating_btn_bank_account)
        floatingActionButton.startAnimation(ttb)
        recyclerView.startAnimation(ttb)

        floatingActionButton.setOnClickListener{
            val mDialogView = LayoutInflater.from( context).inflate(R.layout.bank_account_dialog, null);
            val mItemView = LayoutInflater.from(context).inflate(R.layout.item_bank_account, null )

            val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogView)
                    .setTitle("Bank Account")
            val mAlertDialog = mBuilder.show()

        salaryAccount = mDialogView.findViewById(R.id.salary_account)
        bankName = mDialogView.findViewById(R.id.bank_name)
        number = mDialogView.findViewById(R.id.number)
        nameSurname = mDialogView.findViewById(R.id.person_name_surname)

        salaryAcountTV = mItemView.findViewById(R.id.salary_account_tv)
        bankNameTv = mItemView.findViewById(R.id.bank_name_tv)
        numberTV = mItemView.findViewById(R.id.number_tv)
        nameSurnameTV = mItemView.findViewById(R.id.person_name_surname_tv)

        cancelActionButton = mDialogView.findViewById(R.id.cancel_dialog_btn)
        saveActionButton = mDialogView.findViewById<Button>(R.id.save_dialog_btn);


            saveActionButton.setOnClickListener {
                saveActionButton.startAnimation(atb)
                mAlertDialog.dismiss()
                var  card = Card()
                card.account = salaryAccount.text.toString()
                card.bankName = bankName.text.toString()
                card.accountNumber = number.text.toString()
                card.nameSurname = nameSurname.text.toString()

                cardList.add(card)
                customAdapter.notifyDataSetChanged()
                println("end")

            }
            cancelActionButton.setOnClickListener() {
                mAlertDialog.dismiss()
            }


        }
        return root
    }
}














