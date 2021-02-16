package com.aldidwiki.myquizapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.databinding.ActivityMainBinding
import com.aldidwiki.myquizapp.helper.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.navigation_home, R.id.navigation_dashboard
                )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.label) {
                resources.getString(R.string.title_home) -> binding.navView.show(true)
                resources.getString(R.string.title_dashboard) -> binding.navView.show(true)
                else -> binding.navView.show(false)
            }
        }
    }
}