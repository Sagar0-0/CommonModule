package fit.asta.health.data.profile.remote.model

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status


data class ProfileAvailableStatus(
    @SerializedName("status") val status: Status,
    @SerializedName("flag") val flag: Boolean,
)