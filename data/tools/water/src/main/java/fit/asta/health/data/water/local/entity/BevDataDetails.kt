package fit.asta.health.data.water.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bevDetails")
data class BevDataDetails(
    @PrimaryKey(autoGenerate = false) var id : Int,
    @ColumnInfo(name = "WaterQuantity") val waterQuantity : Int,
    @ColumnInfo(name = "CoconutQuantity") val coconutQuantity : Int,
    @ColumnInfo(name = "FirstPrefQuantity") val firstPrefQuantity : Int,
    @ColumnInfo(name = "SecondPrefQuantity") val secondPrefQuantity : Int,
    @ColumnInfo(name = "RecentAddQuantity") val recentAddQuantity : Int,
)
