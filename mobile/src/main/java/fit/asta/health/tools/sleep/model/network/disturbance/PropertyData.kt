package fit.asta.health.tools.sleep.model.network.disturbance

import com.google.gson.annotations.SerializedName

data class PropertyData(
    @SerializedName("code")
    val code: String,
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("since")
    val since: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: String
)