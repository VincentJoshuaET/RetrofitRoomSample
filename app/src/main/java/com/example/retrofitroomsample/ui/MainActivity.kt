package com.example.retrofitroomsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.retrofitroomsample.R
import com.example.retrofitroomsample.binding.viewBinding
import com.example.retrofitroomsample.databinding.ActivityMainBinding
import com.example.retrofitroomsample.viewmodel.DataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val viewModel: DataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        val navController =
            (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).navController

        val fragments = setOf(R.id.fragment_splash, R.id.fragment_login, R.id.fragment_home)

        toolbar.setupWithNavController(navController, AppBarConfiguration(fragments))

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragment_home -> {
                    toolbar.menu.clear()
                    toolbar.inflateMenu(R.menu.menu_home)
                }
                else -> toolbar.menu.clear()
            }
        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_profile -> {
                    navController.navigate(R.id.dialog_profile)
                    return@setOnMenuItemClickListener true
                }
                R.id.item_logout -> {
                    viewModel.logout()
                    navController.navigate(R.id.action_home_to_auth)
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
    }

}