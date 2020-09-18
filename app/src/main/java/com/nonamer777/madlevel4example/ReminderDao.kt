package com.nonamer777.madlevel4example

import androidx.room.*

@Dao
interface ReminderDao {

    @Query("select * from reminderTable")
    suspend fun getAllReminders(): List<Reminder>

    @Insert
    suspend fun saveReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)
}
