package com.example.themoviedb.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import androidx.databinding.ViewDataBinding
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
    var actionMode: ActionMode? = null
    val actionModeCallbacks = HashMap<MovieListFragment<ViewDataBinding>, MovieActionModeCallback>()

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

        navController.addOnDestinationChangedListener{ _, _, _ ->
            actionMode?.finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
    }

    fun setupToolbar() {
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    fun createActionModeForMovie(
        fragment: MovieListFragment<ViewDataBinding>,
        actionCallBack: MovieActionModeCallback
    ) {
        actionModeCallbacks[fragment] = actionCallBack
        if (actionMode == null)
            actionMode = startSupportActionMode(object : ActionMode.Callback {
                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    val results =
                        actionModeCallbacks.values.map { it.onActionItemClicked(mode, item) }
                    return results.firstOrNull { true } ?: false
                }

                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    actionModeCallbacks.values.forEach { it.onCreateActionMode(mode, menu) }
                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    actionModeCallbacks.values.forEach { it.onPrepareActionMode(mode, menu) }
                    return true
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                    actionModeCallbacks.values.forEach { it.onDestroyActionMode(mode) }
                }

            })
    }

    fun cancelActionMode(fragment: MovieListFragment<ViewDataBinding>) {
        actionModeCallbacks[fragment].let {
            it?.onDestroyActionMode(actionMode)
        }
        actionModeCallbacks.remove(fragment)
        if (actionModeCallbacks.isEmpty()) {
            actionMode?.finish()
            actionMode = null
        }
    }
}