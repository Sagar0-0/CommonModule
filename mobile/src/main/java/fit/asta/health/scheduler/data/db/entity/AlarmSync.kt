package fit.asta.health.scheduler.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ALARM_SYNC_TABLE")
data class AlarmSync(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var alarmId: Long = 0,
    @ColumnInfo(name = "scheduleId")
    var scheduleId: String = ""
)