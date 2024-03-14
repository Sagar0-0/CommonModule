package fit.asta.health.data.water.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ConsumptionHistory")
data class ConsumptionHistory(
    @PrimaryKey val date : String,
    @ColumnInfo(name = "Goal") val goal : Int,
    @ColumnInfo(name = "Total Consumed") val totalConsumed : Int,
    @ColumnInfo(name = "Remaining") val remainingToConsume : Int
)
