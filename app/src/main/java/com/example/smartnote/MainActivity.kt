package com.example.smartnote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
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
        DataStoreHandler.saveArrayListHoliday()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
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
                val currentFragment = supportFragmentManager.fragments.first().childFragmentManager.fragments.first()
                when (currentFragment) {
                    is ShoppingFragment -> {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        val shareBody = DataStoreHandler.getShoppings().toString()
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    }
                    is HolidayFragment -> {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        val shareBody = DataStoreHandler.getArrayListHolidays().toString()
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    }
                    is BankAccountFragment -> {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        val shareBody = DataStoreHandler.getArrayListCards().toString()
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    }
                    is SalesFragment -> {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        val shareBody = DataStoreHandler.getArrayListSales().toString()
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    }
                    is CredentialsFragment -> {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        val shareBody = DataStoreHandler.getArrayListCredentials().toString()
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    }
                    is TextNoteFragment -> {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        val shareBody = DataStoreHandler.getArrayListNotes().toString()
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    }
                }
                return true
            }
            R.id.delete -> {
                val currentFragment = supportFragmentManager.fragments.first().childFragmentManager.fragments.first()
                when (currentFragment) {
                    is ShoppingFragment -> {
                        DataStoreHandler.shoppingItems.removeAll(DataStoreHandler.shoppingItems)
                        currentFragment.customAdapterShopping.notifyDataSetChanged()
                    }
                    is HolidayFragment -> {
                        DataStoreHandler.holidays.removeAll(DataStoreHandler.holidays)
                        currentFragment.customAdapterHoliday.notifyDataSetChanged()
                    }
                    is BankAccountFragment -> {
                        DataStoreHandler.cards.removeAll(DataStoreHandler.cards)
                        currentFragment.customAdapter.notifyDataSetChanged()

                    }
                    is SalesFragment -> {
                        DataStoreHandler.sales.removeAll(DataStoreHandler.sales)
                        currentFragment.customAdapterSales.notifyDataSetChanged()

                    }
                    is CredentialsFragment -> {
                        DataStoreHandler.credentials.removeAll(DataStoreHandler.credentials)
                        currentFragment.customAdapterCredentials.notifyDataSetChanged()

                    }
                    is TextNoteFragment -> {
                        DataStoreHandler.notes.removeAll(DataStoreHandler.notes)
                        currentFragment.customAdapterNote.notifyDataSetChanged()
                    }
                }
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