package com.nonamer777.madlevel5example.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nonamer777.madlevel5example.R
import com.nonamer777.madlevel5example.model.Reminder
import com.nonamer777.madlevel5example.model.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_reminders.*

/**
 * A simple [Fragment] subclass where the created Reminders are shown.
 */
class RemindersFragment: Fragment() {

    /** The list of Reminders. */
    private val reminders = arrayListOf<Reminder>()

    /** The Adapter that transforms the data of Reminders into presentable data. */
    private val reminderAdapter = ReminderAdapter(reminders)

    private val viewModel: ReminderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_reminders, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        // Listener to the result of the Add Reminder Fragment.
        observeAddReminderResult()
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
        viewModel.reminders.observe(viewLifecycleOwner, { reminders ->
            this@RemindersFragment.reminders.clear()
            this@RemindersFragment.reminders.addAll(reminders)

            reminderAdapter.notifyDataSetChanged()
        })
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
                viewModel.deleteReminder(reminderToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }
}
