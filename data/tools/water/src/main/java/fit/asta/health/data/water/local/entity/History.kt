package fit.asta.health.data.water.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true) var id : Int,
    @ColumnInfo(name = "Beverage") val bevName : String,
    @ColumnInfo(name = "RecentAddedQuantity") val recentAddedQuantity : Int

)
