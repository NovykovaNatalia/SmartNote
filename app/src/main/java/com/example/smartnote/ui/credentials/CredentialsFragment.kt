package com.example.smartnote.ui.credentials

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartnote.DataStoreHandler
import com.example.smartnote.R
import com.example.smartnote.ui.bank_account.BankAccountFragment
import com.example.smartnote.ui.holiday.HolidayFragment
import com.example.smartnote.ui.sales.SalesFragment
import com.example.smartnote.ui.settings.SettingsFragment
import com.example.smartnote.ui.shopping.ShoppingFragment
import com.example.smartnote.ui.text_note.TextNoteFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CredentialsFragment : Fragment() {
    lateinit var floatingActionButtonCredentials: FloatingActionButton
    lateinit var saveActionButtonCredentials: Button
    lateinit var cancelActionButtonCredentials: Button
    lateinit var dialog_et_credentials: EditText
    lateinit var dialog_et_refetence: EditText
    lateinit var credential_tv: TextView
    lateinit var reference_tv: TextView
    lateinit var recyclerViewCredentials: RecyclerView
    lateinit var searchViewCredentials: SearchView
    lateinit var listCredentials: ArrayList<Credentials>
    lateinit var customAdapterCredentials: CustomAdapterCredentials


    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        listCredentials = DataStoreHandler.credentials
        val root = inflater.inflate(R.layout.fragment_credentials, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val atb = AnimationUtils.loadAnimation(context, R.anim.atb)
        val btt = AnimationUtils.loadAnimation(context, R.anim.btt)
        val btn = AnimationUtils.loadAnimation(context, R.anim.btn)



        searchViewCredentials = root.findViewById(R.id.searchViewCredentials)
        recyclerViewCredentials = root.findViewById(R.id.recyclerViewCredentials)
        recyclerViewCredentials.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        customAdapterCredentials = CustomAdapterCredentials(listCredentials, context)
       //* Check send one item
        recyclerViewCredentials.adapter = customAdapterCredentials

        customAdapterCredentials.notifyDataSetChanged()

        floatingActionButtonCredentials = root.findViewById(R.id.floating_btn_credentials)
        searchViewCredentials.startAnimation(ttb)
        recyclerViewCredentials.startAnimation(ttb)
        floatingActionButtonCredentials.startAnimation(ttb)

        floatingActionButtonCredentials.setOnClickListener {
            val mDialogViewCredentials = LayoutInflater.from(context).inflate(R.layout.credentials_dialog, null);
            val mItemViewCredentials = LayoutInflater.from(context).inflate(R.layout.item_credentials, null)

            val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialogViewCredentials)
                    .setTitle("Credentials")
            val mAlertDialog = mBuilder.show()

            dialog_et_credentials = mDialogViewCredentials.findViewById(R.id.credential)
            dialog_et_refetence = mDialogViewCredentials.findViewById(R.id.reference)

            credential_tv = mItemViewCredentials.findViewById(R.id.name_credentials_tv)
            reference_tv = mItemViewCredentials.findViewById(R.id.reference_credential_tv)

            cancelActionButtonCredentials = mDialogViewCredentials.findViewById(R.id.cancel_dialog_credentials)
            saveActionButtonCredentials = mDialogViewCredentials.findViewById(R.id.save_dialog_credentials);

            saveActionButtonCredentials.setOnClickListener {
                mAlertDialog.dismiss()
                if(dialog_et_credentials.text.isNotEmpty() && dialog_et_refetence.text.isNotEmpty()) {
                    var cardCredentils = Credentials()
                    cardCredentils.credential = dialog_et_credentials.text.toString()
                    cardCredentils.reference = dialog_et_refetence.text.toString()

                    listCredentials.add(cardCredentils)
                } else {
                    Toast.makeText(context, "Put value!", Toast.LENGTH_LONG).show()
                }

                customAdapterCredentials.notifyDataSetChanged()
            }

            cancelActionButtonCredentials.setOnClickListener() {
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
                is CredentialsFragment -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    var sharStr = DataStoreHandler.credentials.toString()
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
                is CredentialsFragment -> {
                    DataStoreHandler.credentials.removeAll(DataStoreHandler.credentials)
                    currentFragment.customAdapterCredentials.notifyDataSetChanged()
                    DataStoreHandler.saveArrayListCredentials()
                }
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}

