package fit.asta.health.common.multiselect.data


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class UserInputs(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("statusDTO")
    val status: Status = Status()
)