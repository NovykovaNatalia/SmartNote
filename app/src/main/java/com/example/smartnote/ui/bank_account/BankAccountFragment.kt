package com.example.smartnote.ui.bank_account

import CardAdapter
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.DataStoreHandler
import com.example.smartnote.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BankAccountFragment : Fragment() {
    lateinit var cardAdapter: CardAdapter

    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val cardList = DataStoreHandler.cards

        val root = inflater.inflate(R.layout.fragment_bank_account, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)

        val recyclerView : RecyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        cardAdapter = CardAdapter(cardList, context)
        recyclerView.adapter = cardAdapter
        cardAdapter.notifyDataSetChanged()
        recyclerView.startAnimation(ttb)

        val bankAccountSv : SearchView = root.findViewById(R.id.searchView)
        bankAccountSv.startAnimation(ttb)

        val fab: FloatingActionButton = root.findViewById(R.id.floating_btn_bank_account)
        fab.startAnimation(ttb)
        fab.setOnClickListener {
            val dialogView = LayoutInflater.from( context).inflate(R.layout.bank_account_dialog, null);
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_bank_account, null )

            val builder = AlertDialog.Builder(context)
                    .setView(dialogView)
                    .setTitle(getString(R.string.bank_account))
            val alertDialog = builder.show()

            val salaryAccountEt : EditText = dialogView.findViewById(R.id.salary_account)
            val bankNameEt : EditText = dialogView.findViewById(R.id.bank_name)
            val numberEt : EditText = dialogView.findViewById(R.id.number)
            val nameSurnameEt : EditText = dialogView.findViewById(R.id.person_name_surname)

            val saveBtn : Button = dialogView.findViewById(R.id.save_dialog_bank)
            saveBtn.setOnClickListener {
                if(salaryAccountEt.text.isNotEmpty()
                        && bankNameEt.text.isNotEmpty()
                        && numberEt.text.isNotEmpty()
                        && nameSurnameEt.text.isNotEmpty()) {
                    alertDialog.dismiss()
                    var card = Card()
                    card.account = salaryAccountEt.text.toString()
                    card.bankName = bankNameEt.text.toString()
                    card.accountNumber = numberEt.text.toString()
                    card.nameSurname = nameSurnameEt.text.toString()
                    cardList.add(card)
                } else {
                    Toast.makeText(context, getString(R.string.put_values), Toast.LENGTH_LONG).show()
                }
                cardAdapter.notifyDataSetChanged()
            }

            val cancelBtn : Button = dialogView.findViewById(R.id.cancel_dialog_bank)
            cancelBtn.setOnClickListener() {
                alertDialog.dismiss()
            }
        }
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        menu.findItem(R.id.add).setVisible(false)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
        if (id == R.id.share) {
            when (currentFragment) {
                is BankAccountFragment -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    var sharStr = DataStoreHandler.cards.toString()
                    sharStr = sharStr.replace('[', ' ')
                    sharStr = sharStr.replace(']', ' ')
                    sharStr = sharStr.replace(",", "")
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, sharStr)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, sharStr)
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_one)))
                }
            }
            return true
        }
        if (id == R.id.delete) {
            when (currentFragment) {
                is BankAccountFragment -> {
                    DataStoreHandler.cards.removeAll(DataStoreHandler.cards)
                    currentFragment.cardAdapter.notifyDataSetChanged()
                    DataStoreHandler.saveArrayListCards()
                }
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}














