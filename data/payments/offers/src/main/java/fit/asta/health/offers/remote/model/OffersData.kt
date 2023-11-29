package fit.asta.health.offers.remote.model

import com.google.gson.annotations.SerializedName

data class OffersData(
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("ttl")
    val title: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("discount")
    val discount: Int = 0,
    @SerializedName("code")
    val code: String = "",
    @SerializedName("startDate")
    val startDate: String = "",
    @SerializedName("endDate")
    val endDate: String = ""
)