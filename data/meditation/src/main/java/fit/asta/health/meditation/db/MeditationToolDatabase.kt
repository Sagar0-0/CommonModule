package fit.asta.health.meditation.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [MeditationData::class], version = 1)
abstract class MeditationToolDatabase : RoomDatabase() {
    abstract fun meditationDao(): MeditationDao
}