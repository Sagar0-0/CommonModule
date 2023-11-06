package fit.asta.health.data.profile.remote.model

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("flag")
    val flag: Boolean = false,
)