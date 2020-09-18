package com.nonamer777.madlevel4example.model.reminder

import androidx.room.*

@Dao
interface ReminderDao {

    @Query("select * from reminderTable")
    fun getAllReminders(): List<Reminder>

    @Insert
    fun saveReminder(reminder: Reminder)

    @Update
    fun updateReminder(reminder: Reminder)

    @Delete
    fun deleteReminder(reminder: Reminder)
}
