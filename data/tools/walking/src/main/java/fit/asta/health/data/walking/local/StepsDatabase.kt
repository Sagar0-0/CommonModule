package fit.asta.health.data.walking.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fit.asta.health.data.walking.local.model.AllDay
import fit.asta.health.data.walking.local.model.Day
import fit.asta.health.data.walking.local.utils.DateTimeConverter

@Database(entities = [Day::class, AllDay::class], version = 1)
@TypeConverters(DateTimeConverter::class)
abstract class StepsDatabase : RoomDatabase() {
    abstract fun dayDao(): DayDao
}
