package fit.asta.health.profile.model.domain

import com.google.gson.annotations.SerializedName


data class UserProfileAvailable(
    @SerializedName("flag")
    val flag: Boolean,
    @SerializedName("msg")
    val message: String
)