package fit.asta.health.profile.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status
import fit.asta.health.profile.model.domain.UserProfileAvailable


data class NetUserProfileAvailableRes(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val userProfileStatus: UserProfileAvailable
)