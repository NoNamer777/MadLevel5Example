package com.nonamer777.madlevel4example.model.reminder

import android.content.Context

public class ReminderRepository(context: Context) {

    private var reminderDao: ReminderDao

    init {
        val reminderDatabase = ReminderDatabase.getDatabase(context)

        reminderDao = reminderDatabase!!.reminderDao()
    }

    fun getAllReminder(): List<Reminder> = reminderDao.getAllReminders()

    fun saveReminder(reminder: Reminder) = reminderDao.saveReminder(reminder)

    fun updateReminder(reminder: Reminder) = reminderDao.updateReminder(reminder)

    fun deleteReminder(reminder: Reminder) = reminderDao.deleteReminder(reminder)
}
