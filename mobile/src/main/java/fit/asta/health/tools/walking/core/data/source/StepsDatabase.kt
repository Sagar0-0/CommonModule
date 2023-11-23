package fit.asta.health.tools.walking.core.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fit.asta.health.tools.walking.core.data.source.util.Converters
import fit.asta.health.tools.walking.core.domain.model.Day

@Database(entities = [StepsData::class, Day::class], version = 1)
@TypeConverters(Converters::class)
abstract class StepsDatabase : RoomDatabase() {
    abstract fun stepsDataDAO(): StepsDataDao
    abstract fun dayDao(): DayDao
}
