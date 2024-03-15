package fit.asta.health.data.water.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bevQuantity")
data class BevQuantityConsumed(
    @PrimaryKey(autoGenerate = true) var id : Int,
    @ColumnInfo(name = "Name") val bev: String,
    @ColumnInfo(name = "Quantity") val qty: Double,
    @ColumnInfo(name = "Uid") val uid: String
)
