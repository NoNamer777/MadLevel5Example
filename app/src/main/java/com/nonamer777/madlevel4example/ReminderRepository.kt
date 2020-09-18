package com.nonamer777.madlevel4example

import android.content.Context

public class ReminderRepository(context: Context) {

    private var reminderDao: ReminderDao

    init {
        val reminderDatabase = ReminderDatabase.getDatabase(context)

        reminderDao = reminderDatabase!!.reminderDao()
    }

    suspend fun getAllReminder(): List<Reminder> = reminderDao.getAllReminders()

    suspend fun saveReminder(reminder: Reminder) = reminderDao.saveReminder(reminder)

    suspend fun updateReminder(reminder: Reminder) = reminderDao.updateReminder(reminder)

    suspend fun deleteReminder(reminder: Reminder) = reminderDao.deleteReminder(reminder)
}
