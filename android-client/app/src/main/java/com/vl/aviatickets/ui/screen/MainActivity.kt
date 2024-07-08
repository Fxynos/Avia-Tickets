package com.vl.aviatickets.ui.screen

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vl.aviatickets.NavGlobalDirections
import com.vl.aviatickets.R
import com.vl.aviatickets.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = (
                supportFragmentManager.findFragmentById(binding.navHost.id) as NavHostFragment
            ).navController

        binding.navMenu.setOnItemSelectedListener(this::onMenuItemSelected)
    }

    /**
     * @return `true` to show item as selected
     */
    private fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        navController.navigate(when (menuItem.itemId) {
            R.id.item_tickets -> NavGlobalDirections.actionGlobalTickets()
            R.id.item_hotels -> NavGlobalDirections.actionGlobalHotels()
            R.id.item_shorter -> NavGlobalDirections.actionGlobalShorter()
            R.id.item_subscriptions -> NavGlobalDirections.actionGlobalSubscriptions()
            R.id.item_profile -> NavGlobalDirections.actionGlobalProfile()
            else -> throw RuntimeException() // unreachable
        }) // TODO update selected item on controller navigate up
        return true
    }
}