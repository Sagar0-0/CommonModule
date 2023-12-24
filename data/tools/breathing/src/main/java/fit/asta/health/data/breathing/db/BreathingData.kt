package fit.asta.health.data.breathing.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("breathing_data")
data class BreathingData(
    @PrimaryKey
    val date: Int,
    @ColumnInfo(name = "appliedAngleDistance") val appliedAngleDistance: Float,
    @ColumnInfo(name = "start") val start: Boolean,
    @ColumnInfo(name = "time") val time: Long
)
