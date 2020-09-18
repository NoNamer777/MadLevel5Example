package com.nonamer777.madlevel4example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)

        fab.setOnClickListener {
            navController.navigate(R.id.action_remindersFragment_to_addReminderFragment)
        }

        toggleFab()
    }

    private fun toggleFab() = navController.addOnDestinationChangedListener { _, destination, _ ->

        if (destination.id in arrayOf(R.id.addReminderFragment)) fab.hide()
        else fab.show()
    }
}
