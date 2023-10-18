package fit.asta.health.data.profile.remote.model

import com.google.gson.annotations.SerializedName

data class UserProfileAvailableResponse(
    @SerializedName("flag") val flag: Boolean = false
)