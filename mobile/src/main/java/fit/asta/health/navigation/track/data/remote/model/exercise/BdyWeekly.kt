package fit.asta.health.navigation.track.data.remote.model.exercise

import com.google.gson.annotations.SerializedName

data class BdyWeekly(
    @SerializedName("Arms")
    val arms: Int,
    @SerializedName("Buttocks")
    val buttocks: Int,
    @SerializedName("Core")
    val core: Int,
    @SerializedName("Full Body")
    val fullBody: Int,
    @SerializedName("Legs")
    val legs: Int
)