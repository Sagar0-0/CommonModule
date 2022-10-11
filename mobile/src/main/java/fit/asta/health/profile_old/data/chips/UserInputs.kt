package fit.asta.health.profile_old.data.chips


import com.google.gson.annotations.SerializedName

data class UserInputs(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("statusDTO")
    val status: Status = Status()
)