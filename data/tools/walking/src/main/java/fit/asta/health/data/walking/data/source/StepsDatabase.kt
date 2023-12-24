package fit.asta.health.data.walking.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fit.asta.health.data.walking.data.source.util.Converters
import fit.asta.health.data.walking.domain.model.AllDay
import fit.asta.health.data.walking.domain.model.Day

@Database(entities = [Day::class, AllDay::class], version = 1)
@TypeConverters(Converters::class)
abstract class StepsDatabase : RoomDatabase() {
    abstract fun dayDao(): DayDao
}
