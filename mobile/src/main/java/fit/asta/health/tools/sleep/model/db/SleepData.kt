package fit.asta.health.tools.sleep.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "sleep_data")
data class SleepData(

    @PrimaryKey(autoGenerate = true)
    val key: Int,
    @ColumnInfo(name = "start_time")
    val startTime: LocalDateTime
)
