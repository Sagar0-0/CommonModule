package fit.asta.health.tools.water.model.network.get


import com.google.gson.annotations.SerializedName

data class BeverageInfo(
    @SerializedName("bevIds")
    val bevIds: List<String>,
    @SerializedName("id")
    val id: String,
    @SerializedName("qty")
    val qty: Qty,
    @SerializedName("uid")
    val uid: String
)