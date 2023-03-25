package fit.asta.health.tools.walking.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps_data")
data class StepsData(
    @PrimaryKey val date: Int,
    @ColumnInfo(name = "initial_steps") val initialSteps: Int,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "all_steps") val allSteps: Int,
    @ColumnInfo(name = "time") val time: Long

)