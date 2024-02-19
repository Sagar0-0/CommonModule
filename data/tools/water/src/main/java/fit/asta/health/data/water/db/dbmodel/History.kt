package fit.asta.health.data.water.check.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.processing.Generated

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true) var id : Int,
    @ColumnInfo(name = "Beverage") val bevName : String,
    @ColumnInfo(name = "RecentAddedQuantity") val recentAddedQuantity : Int

)
