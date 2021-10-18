package com.example.smartnote.ui.bank_account

import CustomAdapter
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.DataStoreHandler
import com.example.smartnote.R
import com.example.smartnote.ui.credentials.CredentialsFragment
import com.example.smartnote.ui.holiday.HolidayFragment
import com.example.smartnote.ui.sales.SalesFragment
import com.example.smartnote.ui.settings.SettingsFragment
import com.example.smartnote.ui.shopping.ShoppingFragment
import com.example.smartnote.ui.text_note.TextNoteFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
    lateinit var cardList: ArrayList<Card>
    lateinit var customAdapter: CustomAdapter
    lateinit var searchViewBankAccount: SearchView



    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cardList = DataStoreHandler.cards

        val root = inflater.inflate(R.layout.fragment_bank_account, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val atb = AnimationUtils.loadAnimation(context, R.anim.atb)
        val btt = AnimationUtils.loadAnimation(context, R.anim.btt)
        val btn = AnimationUtils.loadAnimation(context, R.anim.btn)

        recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        customAdapter = CustomAdapter(cardList, context)
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

        cancelActionButton = mDialogView.findViewById(R.id.cancel_dialog_bank)
        saveActionButton = mDialogView.findViewById(R.id.save_dialog_bank );


            saveActionButton.setOnClickListener {
                if(salaryAccount.text.isNotEmpty() && bankName.text.isNotEmpty() && number.text.isNotEmpty()
                        && nameSurname.text.isNotEmpty()) {
                    mAlertDialog.dismiss()
                    var card = Card()
                    card.account = salaryAccount.text.toString()
                    card.bankName = bankName.text.toString()
                    card.accountNumber = number.text.toString()
                    card.nameSurname = nameSurname.text.toString()
                    cardList.add(card)
                } else {
                    Toast.makeText(context, "Put values!", Toast.LENGTH_LONG).show()
                }
                customAdapter.notifyDataSetChanged()
            }
            cancelActionButton.setOnClickListener() {
                mAlertDialog.dismiss()
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
        if (id == R.id.settings) {
            val fragmentManager: FragmentManager = activity?.supportFragmentManager!!
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val setFragment = SettingsFragment()
            fragmentTransaction.replace(R.id.settings, setFragment)
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true
        }
        if (id == R.id.share) {
            val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
            when (currentFragment) {
                is BankAccountFragment -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    var sharStr = DataStoreHandler.cards.toString()
                    sharStr = sharStr.replace('[', ' ')
                    sharStr = sharStr.replace(']', ' ')
                    sharStr = sharStr.replace(",", "")
                    val shareBody = sharStr
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                }
            }
            return true
        }
        if (id == R.id.delete) {
            val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
            when (currentFragment) {
                is BankAccountFragment -> {
                    DataStoreHandler.cards.removeAll(DataStoreHandler.cards)
                    currentFragment.customAdapter.notifyDataSetChanged()
                    DataStoreHandler.saveArrayListCards()
                }
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}














