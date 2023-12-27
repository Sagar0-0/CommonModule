package fit.asta.health.data.exercise.model.network


import com.google.gson.annotations.SerializedName

data class PrcX(
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("ttl")
    val title: String,
    @SerializedName("values")
    val values: List<Value>
)