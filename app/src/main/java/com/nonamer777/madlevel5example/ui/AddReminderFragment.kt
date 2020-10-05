package com.nonamer777.madlevel5example.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nonamer777.madlevel5example.R
import com.nonamer777.madlevel5example.model.Reminder
import com.nonamer777.madlevel5example.model.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_add_reminder.*

/**
 * A simple [Fragment] subclass that is used to add Reminders to the Reminders Database.
 */
class AddReminderFragment: Fragment() {

    private val viewModel: ReminderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configure Click Event Listener.
        btnAddReminder.setOnClickListener { onAddReminder() }
    }

    /** Handles adding a Reminder. */
    private fun onAddReminder() {
        val reminderText = etReminder.text.toString()

        if (reminderText.isNotBlank()) {
            viewModel.saveReminder(Reminder(reminderText))

            /* Set the data as fragmentResult,
             * we are listening for REQ_REMINDER_KEY in RemindersFragment! */
            findNavController().popBackStack()
        } else {
            Toast.makeText(
                activity,
                R.string.not_valid_reminder, Toast.LENGTH_SHORT
            ).show()
        }
    }
}
