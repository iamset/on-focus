package com.focusg.focusgroup.presentation

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.focusg.focusgroup.R
import com.focusg.focusgroup.presentation.auth.AuthViewModel
import com.focusg.focusgroup.presentation.users.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId){
        R.id.action_create -> {
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.createFocusGroupFragment)
            true
        }
        R.id.action_logout -> {
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }



}