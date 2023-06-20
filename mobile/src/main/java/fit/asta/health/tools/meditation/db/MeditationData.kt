package fit.asta.health.tools.meditation.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "meditation_data")
data class MeditationData(
    @PrimaryKey val date: Int,
    @ColumnInfo(name = "appliedAngleDistance") val appliedAngleDistance: Float,
    @ColumnInfo(name = "start") val start:Boolean,
    @ColumnInfo(name = "time") val time:Long
)