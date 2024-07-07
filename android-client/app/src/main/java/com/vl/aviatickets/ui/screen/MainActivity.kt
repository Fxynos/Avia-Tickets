package com.vl.aviatickets.ui.screen

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vl.aviatickets.R
import com.vl.aviatickets.databinding.ActivityMainBinding

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
            R.id.item_tickets -> R.id.action_global_tickets
            R.id.item_hotels -> R.id.action_global_hotels
            R.id.item_shorter -> R.id.action_global_shorter
            R.id.item_subscriptions -> R.id.action_global_subscriptions
            R.id.item_profile -> R.id.action_global_profile
            else -> throw RuntimeException() // unreachable
        })
        return true
    }
}