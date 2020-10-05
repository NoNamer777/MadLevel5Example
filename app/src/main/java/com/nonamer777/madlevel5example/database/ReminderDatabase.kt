package com.nonamer777.madlevel5example.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nonamer777.madlevel5example.dao.ReminderDao
import com.nonamer777.madlevel5example.model.Reminder

@Database(entities = [Reminder::class], version = 1, exportSchema = false)
abstract class ReminderDatabase: RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

    companion object {
        private const val DATABASE_NAME = "REMINDER_DATABASE"

        @Volatile
        private var reminderDatabaseInstance: ReminderDatabase? = null

        fun getDatabase(context: Context): ReminderDatabase? {

            if (reminderDatabaseInstance == null) {
                synchronized(ReminderDatabase::class.java) {

                    if (reminderDatabaseInstance == null) {
                        reminderDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            ReminderDatabase::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return reminderDatabaseInstance
        }
    }
}
