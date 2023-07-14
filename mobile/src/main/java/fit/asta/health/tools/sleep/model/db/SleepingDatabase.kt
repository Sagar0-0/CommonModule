package fit.asta.health.tools.sleep.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SleepingLocalData::class], version = 1)
abstract class SleepingDatabase : RoomDatabase() {
    abstract fun sleepingDao(): SleepingDao
}