package fit.asta.health.tools.breathing.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [BreathingData::class], version = 1)
abstract class BreathingDatabase : RoomDatabase() {
    abstract fun breathingDao(): BreathingDao
}