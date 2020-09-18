package com.nonamer777.madlevel4example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_reminder.view.*

class ReminderAdapter(private val reminders: List<Reminder>):
    RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun dataBind(reminder: Reminder) {
            itemView.tvReminderCard.text = reminder.reminderText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.dataBind(reminders[position])

    override fun getItemCount(): Int =
        reminders.size
}
