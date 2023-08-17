package fit.asta.health.scheduler.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fit.asta.health.scheduler.data.db.entity.AlarmEntity
import fit.asta.health.scheduler.data.db.entity.AlarmSync
import fit.asta.health.scheduler.data.db.entity.TagEntity
import fit.asta.health.scheduler.ref.db.AlarmInstanceDao
import fit.asta.health.scheduler.ref.db.AlarmRefDao
import fit.asta.health.scheduler.ref.provider.Alarm
import fit.asta.health.scheduler.ref.provider.AlarmInstance

@Database(
    entities = [AlarmEntity::class, TagEntity::class, AlarmSync::class, Alarm::class, AlarmInstance::class],
    version = 1
)
@TypeConverters(CustomTypeConvertors::class)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
    abstract fun alarmRefDao(): AlarmRefDao
    abstract fun alarmInstanceDao(): AlarmInstanceDao
}