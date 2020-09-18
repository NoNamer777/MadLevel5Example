package com.nonamer777.madlevel4example

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_reminders.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RemindersFragment: Fragment() {

    private val reminders = arrayListOf<Reminder>()
    private val reminderAdapter = ReminderAdapter(reminders)

    private lateinit var reminderRepo: ReminderRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeAddReminderResult()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        reminderRepo = ReminderRepository(requireContext())
        getRemindersFromDatabase()
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, and adapter.
        rvReminders.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvReminders.adapter = reminderAdapter
        rvReminders.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        createItemTouchHelper().attachToRecyclerView(rvReminders)
    }

    /** Observes the output of the `Add Reminder` Fragment and handles its result. */
    private fun observeAddReminderResult() {
        setFragmentResultListener(REQ_REMINDER_KEY) { key, bundle ->
            bundle.getString(BUNDLE_REMINDER_KEY)?.let {
                val reminder = Reminder(null, it)

                reminderRepo.saveReminder(reminder)
                getRemindersFromDatabase()
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
                val reminderToDelete = reminders[viewHolder.adapterPosition]

                reminderRepo.deleteReminder(reminderToDelete)
                getRemindersFromDatabase()
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun getRemindersFromDatabase() {
        val reminders = reminderRepo.getAllReminder()

        this.reminders.clear()
        this.reminders.addAll(reminders)

        reminderAdapter.notifyDataSetChanged()
    }
}
