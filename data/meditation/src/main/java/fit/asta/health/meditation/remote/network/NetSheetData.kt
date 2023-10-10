package fit.asta.health.meditation.remote.network


import com.google.gson.annotations.SerializedName

data class NetSheetData(
    @SerializedName("code")
    val code: String,
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("since")
    val since: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("url")
    val url: String,
    var isSelected: Boolean = false,
)