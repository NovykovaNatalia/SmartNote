package com.nnnd.smartnote.ui.bankaccount

import CardAdapter
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nnnd.smartnote.DataStoreHandler
import com.nnnd.smartnote.LanguageSupportUtils
import com.nnnd.smartnote.R
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
        menu.findItem(R.id.normal).setVisible(false)
        menu.findItem(R.id.clear).setVisible(false)
        menu.findItem(R.id.rubber).setVisible(false)
        menu.findItem(R.id.delete_checked_list).setVisible(false)
        menu.findItem(R.id.add).setVisible(false)
        menu.findItem(R.id.save_end_store).setVisible(false)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == R.id.settings) {
            return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
        }
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
                    sharStr = context?.let { LanguageSupportUtils.castToLangBank(it, sharStr) }!!
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, sharStr)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, sharStr)
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_one)))
                }
            }
            return true
        }
        if (id == R.id.delete) {
            when (currentFragment ) {
                is BankAccountFragment -> {
                    val dialogView = layoutInflater.inflate(R.layout.delete_list_layout, null);
                    val builder = AlertDialog.Builder(context)
                            .setView(dialogView)
                            .setTitle(context?.getString(R.string.delete_the_list))
                    val deleteListAd = builder.show()

                    val noBtn : TextView = dialogView.findViewById(R.id.noBtn)
                    val yesBtn : TextView = dialogView.findViewById(R.id.yesBtn)
                    noBtn.setOnClickListener{
                        deleteListAd.dismiss()
                    }
                    yesBtn.setOnClickListener {
                        if(!DataStoreHandler.cards.isEmpty()){
                            DataStoreHandler.cards.removeAll(DataStoreHandler.cards)
                            currentFragment.cardAdapter.notifyDataSetChanged()
                            DataStoreHandler.saveShoppings()
                            deleteListAd.dismiss()
                        } else {
                            Toast.makeText(context, getString(R.string.list_is_empty), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}














