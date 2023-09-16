package fit.asta.health.data.address.remote.modal

import com.google.gson.annotations.SerializedName

data class MyAddress(
    @SerializedName("cl")
    val selected: Boolean = false,
    @SerializedName("area")
    val area: String = "",
    @SerializedName("block")
    val block: String = "",
    @SerializedName("hn")
    val hn: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("loc")
    val loc: String = "",
    @SerializedName("lon")
    val lon: Double = 0.0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("nearby")
    val nearby: String = "",
    @SerializedName("ph")
    val ph: String = "",
    @SerializedName("pin")
    val pin: String = "",
    @SerializedName("sub")
    val sub: String = "",
    @SerializedName("uid")
    val uid: String = "",
    val addressLine: String = "",
    val shortAddress: String = ""
)