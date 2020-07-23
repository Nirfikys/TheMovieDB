package com.example.themoviedb.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.themoviedb.R
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.homeFragment, R.id.savedListFragment), drawerLayout)

        val navView =
            findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)
    }

    fun setupToolbar() {
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
    }
}