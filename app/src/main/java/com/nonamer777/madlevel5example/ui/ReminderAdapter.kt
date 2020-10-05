package com.nonamer777.madlevel5example.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nonamer777.madlevel5example.R
import com.nonamer777.madlevel5example.model.Reminder
import kotlinx.android.synthetic.main.item_reminder.view.*

class ReminderAdapter(

    /** The list of Reminders in the RecyclerView. */
    private val reminders: List<Reminder>

): RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        /** Binds the Reminder data to the View. */
        fun dataBind(reminder: Reminder) {
            itemView.tvReminderCard.text = reminder.reminderText
        }
    }

    /** Inflates the ViewHolder */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
    )

    /** Binds a specific Reminder to a item in the RecyclerView */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.dataBind(reminders[position])

    /** Gets the number of Items. */
    override fun getItemCount(): Int = reminders.size
}
