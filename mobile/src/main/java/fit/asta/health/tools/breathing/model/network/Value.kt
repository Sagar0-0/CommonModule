package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName

data class Value(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: String
)