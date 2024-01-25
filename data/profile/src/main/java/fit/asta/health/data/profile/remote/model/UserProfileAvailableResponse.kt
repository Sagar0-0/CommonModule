package fit.asta.health.data.profile.remote.model

import com.google.gson.annotations.SerializedName

data class UserProfileAvailableResponse(
    @SerializedName("basic") val isBasicProfileAvailable: Boolean = false,
    @SerializedName("flag") val userProfilePresent: Boolean = false
)