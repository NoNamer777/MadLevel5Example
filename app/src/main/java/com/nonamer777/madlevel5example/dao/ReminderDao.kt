package com.nonamer777.madlevel5example.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nonamer777.madlevel5example.model.Reminder

@Dao
interface ReminderDao {

    @Query("select * from reminderTable")
    fun getAllReminders(): LiveData<List<Reminder>>

    @Insert
    suspend fun saveReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)
}
