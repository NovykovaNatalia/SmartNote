package com.nnnd.smartnote

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale()
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSharedPreferences("AppSettingPref", 0)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.shopping,
                        R.id.events,
                        R.id.bankAccounts,
                        R.id.credentials,
                        R.id.sales,
                        R.id.textNote,
                        R.id.painting,
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
        DataStoreHandler.saveArrayListEvents()
        DataStoreHandler.saveCurrentLang()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun setLocale() {
        val locale = Locale(DataStoreHandler.currentLanguage)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config,
                baseContext.resources.displayMetrics)
    }
}