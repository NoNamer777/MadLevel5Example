package com.nonamer777.madlevel5example.repository

import android.content.Context
import com.nonamer777.madlevel5example.dao.ReminderDao
import com.nonamer777.madlevel5example.database.ReminderDatabase
import com.nonamer777.madlevel5example.model.Reminder

class ReminderRepository(context: Context) {

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
