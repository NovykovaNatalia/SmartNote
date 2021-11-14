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
import com.example.smartnote.ui.settings.SettingsFragment
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
                    DataStoreHandler.credentials.removeAll(DataStoreHandler.credentials)
                    currentFragment.credentialsAdapter.notifyDataSetChanged()
                    DataStoreHandler.saveArrayListCredentials()
                }
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}

