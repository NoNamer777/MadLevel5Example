package com.nonamer777.madlevel5example.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nonamer777.madlevel5example.repository.ReminderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderViewModel(application: Application): AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val reminderRepo = ReminderRepository(application.applicationContext)

    val reminders: LiveData<List<Reminder>> = reminderRepo.getAllReminder()

    fun saveReminder(reminder: Reminder) = ioScope.launch { reminderRepo.saveReminder(reminder) }

    fun deleteReminder(reminder: Reminder) = ioScope.launch { reminderRepo.deleteReminder(reminder) }
}
