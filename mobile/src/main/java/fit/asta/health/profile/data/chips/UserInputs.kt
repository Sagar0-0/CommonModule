package fit.asta.health.profile.data.chips


import com.google.gson.annotations.SerializedName

data class UserInputs(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("status")
    val status: Status = Status()
)