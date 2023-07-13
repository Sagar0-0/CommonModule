package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName

data class Mda(
    @SerializedName("insDtl")
    val instructorDetail: InsDtl,
    @SerializedName("insMda")
    val instructorMda: InsMda
)