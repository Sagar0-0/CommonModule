package fit.asta.health.tools.walking.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StepsData::class], version = 1)
abstract class StepsDatabase : RoomDatabase() {
    abstract fun stepsDataDAO():StepsDataDao
}