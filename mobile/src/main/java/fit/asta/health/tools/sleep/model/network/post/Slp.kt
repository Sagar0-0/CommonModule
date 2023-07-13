package fit.asta.health.tools.sleep.model.network.post

import com.google.gson.annotations.SerializedName

data class Slp(
    @SerializedName("deep")
    val deep: Int,
    @SerializedName("dis")
    val dis: Int,
    @SerializedName("dly")
    val dly: Int,
    @SerializedName("nor")
    val nor: Int
)