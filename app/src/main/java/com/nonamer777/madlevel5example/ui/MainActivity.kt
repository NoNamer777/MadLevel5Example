package com.nonamer777.madlevel5example.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.nonamer777.madlevel5example.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /** The Navigation controller */
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

    /** Hides or Shows the FAB. */
    private fun toggleFab() = navController.addOnDestinationChangedListener { _, destination, _ ->

        if (destination.id in arrayOf(R.id.addReminderFragment)) fab.hide()
        else fab.show()
    }
}
