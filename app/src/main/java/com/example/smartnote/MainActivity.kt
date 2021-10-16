package com.example.smartnote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.smartnote.ui.bank_account.BankAccountFragment
import com.example.smartnote.ui.credentials.CredentialsFragment
import com.example.smartnote.ui.holiday.HolidayFragment
import com.example.smartnote.ui.sales.SalesFragment
import com.example.smartnote.ui.settings.SettingsFragment
import com.example.smartnote.ui.shopping.ShoppingFragment
import com.example.smartnote.ui.text_note.TextNoteFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSharedPreferences("AppSettingPref", 0)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.shopping,
                R.id.holidays,
                R.id.bankAccounts,
                R.id.credentials,
                R.id.sales,
                R.id.settings,
                R.id.textNote,
                R.id.settings
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onStop() {
        super.onStop()
        DataStoreHandler.saveShoppings()
        DataStoreHandler.saveArrayListCards()
        DataStoreHandler.saveArrayListCredentials()
        DataStoreHandler.saveArrayListSales()
        DataStoreHandler.saveArrayListNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
//        val itemShare = menu.findItem(R.id.share)
//        itemShare.setOnMenuItemClickListener {
//            val shareIntent = Intent(Intent.ACTION_SEND)
//            shareIntent.type = "text/plain"
//            val shareBody = "Your body hear"
//            val shareSub = "Your subject hear"
//            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
//            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
//            startActivity(Intent.createChooser(shareIntent, "choose one"))
//            true
//        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e("DEN", "items " + item.itemId)
        return when (item.itemId) {
            R.id.settings -> {
                val fragmentManager: FragmentManager = supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                val setFragment = SettingsFragment()
                fragmentTransaction.replace(R.id.settings, setFragment)
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true
            }
            R.id.share -> {
                Log.e("DEN", "frag is ")
                val currentFragment = supportFragmentManager.fragments.first().childFragmentManager.fragments.first()
                when(currentFragment) {
                    is ShoppingFragment -> {
                        Log.e("Fragment", " shoping")
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        val shareBody = DataStoreHandler.getShoppings().toString()
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    }
                    is HolidayFragment -> {
                        Log.e("Fragment", " Holiday")
                    }
                    is BankAccountFragment -> {
                        Log.e("Fragment", " BankAccountFragment")
                    }
                    is SalesFragment -> {
                        Log.e("Fragment", " SalesFragment")
                    }
                    is CredentialsFragment -> {
                        Log.e("Fragment", " CredentialsFragment")
                    }
                    is TextNoteFragment -> {
                        Log.e("Fragment", " TextNoteFragment")
                    }
                }

                Toast.makeText(applicationContext, "click on share", Toast.LENGTH_LONG).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}