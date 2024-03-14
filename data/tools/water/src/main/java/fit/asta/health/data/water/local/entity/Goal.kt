package fit.asta.health.data.water.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goal")
data class Goal(
    @PrimaryKey(autoGenerate = false) val id : Int,
    @ColumnInfo(name = "Goal") val goal : Int
)
