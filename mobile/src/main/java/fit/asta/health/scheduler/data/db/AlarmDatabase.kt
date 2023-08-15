package fit.asta.health.scheduler.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fit.asta.health.scheduler.data.db.entity.AlarmEntity
import fit.asta.health.scheduler.data.db.entity.AlarmSync
import fit.asta.health.scheduler.data.db.entity.TagEntity

@Database(entities = [AlarmEntity::class, TagEntity::class, AlarmSync::class], version = 1)
@TypeConverters(CustomTypeConvertors::class)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}