package fit.asta.health.profile.data.userprofile


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class UserProfile(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("statusDTO")
    val status: Status = Status()
)