package com.flawlessconcepts.succor

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.ui.*

import com.flawlessconcepts.succor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = binding.drawerLayout

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment),
            binding.drawerLayout
        )
        val navController = this.findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, naveDestination, _ ->
            when (naveDestination.id) {
                R.id.homeFragment, R.id.subjectFragment, R.id.bookmarksFragment, R.id.profileFragment
                ->
                    binding.includeAppBar.includeContent.bottomNav.visibility = View.VISIBLE
                else -> binding.includeAppBar.includeContent.bottomNav.visibility = View.GONE
            }
        }

        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupWithNavController(
            binding.includeAppBar.includeContent.bottomNav,
            navController
        )

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item!!,
            findNavController(R.id.nav_host_fragment)
        )
                || super.onOptionsItemSelected(item)
    }


}