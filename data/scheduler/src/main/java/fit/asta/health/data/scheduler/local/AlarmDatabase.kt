package fit.asta.health.data.scheduler.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fit.asta.health.data.scheduler.local.model.*

@Database(
    entities = [AlarmEntity::class, TagEntity::class, AlarmInstance::class],
    version = 1
)
@TypeConverters(CustomTypeConvertors::class)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
    abstract fun alarmInstanceDao(): AlarmInstanceDao
}