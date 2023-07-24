package fit.asta.health.tools.sleep.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SleepData::class], version = 1)
abstract class SleepToolDatabase : RoomDatabase() {
    abstract fun sleepDao(): SleepDao
}