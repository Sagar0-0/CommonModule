package fit.asta.health.data.water.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.data.water.db.dbmodel.Status


data class BeverageRecentActivity(
    @SerializedName("data")
    val data : BeverageActivity,
    @SerializedName("status")
    val status : Status
)
data class BeverageActivity(
    @SerializedName("id")
    val id : String,
    @SerializedName("totalConsumed")
    val totalConsumed : String = "null",
    @SerializedName("recentQuantity")
    val recentQuantity : Int = 0
)
