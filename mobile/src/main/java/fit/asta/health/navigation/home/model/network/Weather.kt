package fit.asta.health.navigation.home.model.network

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("id")
    val id: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("temp")
    val temperature: String,
    @SerializedName("loc")
    val location: String,
    @SerializedName("rise")
    val sunRise: String,
    @SerializedName("set")
    val sunSet: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("wUrl")
    val wUrl: String,
    @SerializedName("slots")
    val sunSlots: List<SunSlot>
)

data class SunSlot(
    @SerializedName("id")
    val id: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("temp")
    val temperature: String
)