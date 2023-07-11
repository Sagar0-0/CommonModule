package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName

data class InsDtl(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String
)