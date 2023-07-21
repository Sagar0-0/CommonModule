package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName

data class CustomRatioData(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("rto")
    val rto: Rto,
    @SerializedName("stdRto")
    val stdRto: List<StdRto>,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: String
)