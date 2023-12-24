package fit.asta.health.data.water.model.network

import com.google.gson.annotations.SerializedName

data class WaterToolData(
    @SerializedName("bTgt")
    val butterMilkTarget: String,
    @SerializedName("cTgt")
    val coconutTarget: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("jTgt")
    val juiceTarget: String,
    @SerializedName("mTgt")
    val milkTarget: String,
    @SerializedName("prc")
    val prc: Any?=null,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wTgt")
    val waterTarget: String,
    @SerializedName("wea")
    val weather: Boolean
)