package fit.asta.health.tools.sleep.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SleepData::class], version = 1)
@TypeConverters(SleepTypeConverters::class)
abstract class SleepToolDatabase : RoomDatabase() {
    abstract fun sleepDao(): SleepDao
}