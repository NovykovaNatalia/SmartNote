package com.nnnd.smartnote.ui.credentials

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nnnd.smartnote.DataStoreHandler
import com.nnnd.smartnote.LanguageSupportUtils
import com.nnnd.smartnote.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CredentialsFragment : Fragment() {
    lateinit var listCredentials: ArrayList<Credentials>
    lateinit var credentialsAdapter: CredentialsAdapter


    @SuppressLint("WrongConstant")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        listCredentials = DataStoreHandler.credentials
        val root = inflater.inflate(R.layout.fragment_credentials, container, false)
        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)

        val credentialsSv: SearchView = root.findViewById(R.id.searchViewCredentials)
        credentialsSv.startAnimation(ttb)

        val credentialsRv: RecyclerView = root.findViewById(R.id.recyclerViewCredentials)
        credentialsRv.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        credentialsAdapter = CredentialsAdapter(listCredentials, context)
        credentialsRv.adapter = credentialsAdapter
        credentialsRv.startAnimation(ttb)
        credentialsAdapter.notifyDataSetChanged()

        val fab : FloatingActionButton = root.findViewById(R.id.floating_btn_credentials)
        fab.startAnimation(ttb)
        fab.setOnClickListener {
            val credentialsDv = LayoutInflater.from(context)
                    .inflate(R.layout.credentials_dialog, null);

            val builder = AlertDialog.Builder(context)
                    .setView(credentialsDv)
                    .setTitle(getString(R.string.credentials))
            val alertDialog = builder.show()

            val dialogCredentialsEt: EditText = credentialsDv.findViewById(R.id.credential)
            val dialogReferenceEt: EditText = credentialsDv.findViewById(R.id.reference)

            val saveBtn: Button = credentialsDv.findViewById(R.id.save_dialog_credentials);
            saveBtn.setOnClickListener {
                alertDialog.dismiss()
                if(dialogCredentialsEt.text.isNotEmpty() && dialogReferenceEt.text.isNotEmpty()) {
                    val credentials = Credentials()
                    credentials.credential = dialogCredentialsEt.text.toString()
                    credentials.reference = dialogReferenceEt.text.toString()

                    listCredentials.add(credentials)
                } else {
                    Toast.makeText(context, "Put value!", Toast.LENGTH_LONG).show()
                }
                credentialsAdapter.notifyDataSetChanged()
            }

            val  cancelBtn: Button = credentialsDv.findViewById(R.id.cancel_dialog_credentials)
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
        if (id == R.id.settings) {
            return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
        }
        val currentFragment = activity?.supportFragmentManager!!.fragments.first().childFragmentManager.fragments.first()
        if (id == R.id.share) {
            when (currentFragment) {
                is CredentialsFragment -> {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    var sharStr = DataStoreHandler.credentials.toString()
                    sharStr = sharStr.replace('[', ' ')
                    sharStr = sharStr.replace(']', ' ')
                    sharStr = sharStr.replace(",", "")
                    sharStr = context?.let { LanguageSupportUtils.castToLangCredentials(it, sharStr) }!!
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, sharStr)
                    shareIntent.putExtra(Intent.EXTRA_TEXT, sharStr)
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_one)))
                }
            }
            return true
        }
        if (id == R.id.delete) {
            when (currentFragment) {
                is CredentialsFragment -> {
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
                        if(!DataStoreHandler.credentials.isEmpty()){
                            DataStoreHandler.credentials.removeAll(DataStoreHandler.credentials)
                            currentFragment.credentialsAdapter.notifyDataSetChanged()
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

