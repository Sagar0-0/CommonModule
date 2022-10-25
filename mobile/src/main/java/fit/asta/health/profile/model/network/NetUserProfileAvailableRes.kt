package fit.asta.health.profile.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetUserProfileAvailableRes(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val userProfileStatus: UserProfileAvailable
)

data class UserProfileAvailable(
    @SerializedName("flag")
    val flag: Boolean,
    @SerializedName("msg")
    val message: String
)