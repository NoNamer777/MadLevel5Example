package com.nonamer777.madlevel5example.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nonamer777.madlevel5example.R
import com.nonamer777.madlevel5example.model.Reminder
import com.nonamer777.madlevel5example.repository.ReminderRepository
import kotlinx.android.synthetic.main.fragment_reminders.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass where the created Reminders are shown.
 */
class RemindersFragment: Fragment() {

    /** The list of Reminders. */
    private val reminders = arrayListOf<Reminder>()

    /** The Adapter that transforms the data of Reminders into presentable data. */
    private val reminderAdapter = ReminderAdapter(reminders)

    /** The Database connection for the Reminders. */
    private lateinit var remindersRepo: ReminderRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Listener to the result of the Add Reminder Fragment.
        observeAddReminderResult()

        return inflater.inflate(R.layout.fragment_reminders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Instantiate the Reminders Repository.
        remindersRepo = ReminderRepository(requireContext())

        // Get the data from the database and initialize the RecyclerView.
        getRemindersFromDatabase()
        initRecyclerView()
    }

    /** Configures the RecyclerView. */
    private fun initRecyclerView() {
        // Initialize the recycler view with a linear layout manager, and adapter.
        rvReminders.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
        rvReminders.adapter = reminderAdapter

        rvReminders.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        // Add a Touch Helper to the items in the RecyclerView to remove a Reminder.
        createItemTouchHelper().attachToRecyclerView(rvReminders)
    }

    /** Observes the output of the `Add Reminder` Fragment and handles its result. */
    private fun observeAddReminderResult() {
        setFragmentResultListener(REQ_REMINDER_KEY) { key, bundle ->

            bundle.getString(BUNDLE_REMINDER_KEY)?.let {
                // Creates a new Reminder with the inputted text in the Add Reminder Fragment.
                val reminder = Reminder(it)

                // Save the new Reminder into the Database.
                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.IO) { remindersRepo.saveReminder(reminder) }

                    // Update the list of Reminders.
                    getRemindersFromDatabase()
                }
            } ?: Log.e("ReminderFragment", "Request triggered, but empty reminder text!")
        }
    }

    /** Creates a touch helper to recognize when a User swipes an item in the recycler view. */
    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Disables moving an item up and down the list.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            // Callback triggered when a User swipes an item left.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Get the swiped Reminder
                val reminderToDelete = reminders[viewHolder.adapterPosition]

                // Remove the selected Reminder from the Database.
                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.IO) { remindersRepo.deleteReminder(reminderToDelete) }

                    // Update the list of Reminders.
                    getRemindersFromDatabase()
                }
            }
        }
        return ItemTouchHelper(callback)
    }

    /** Requests all Reminders from the Database. */
    private fun getRemindersFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val reminders = withContext(Dispatchers.IO){ remindersRepo.getAllReminder() }

            // Clear the list of Reminders and add all fetched Reminders from the Database.
            this@RemindersFragment.reminders.clear()
            this@RemindersFragment.reminders.addAll(reminders)

            // Notify the RecyclerView that the list of Reminders has been updated.
            reminderAdapter.notifyDataSetChanged()
        }
    }
}
