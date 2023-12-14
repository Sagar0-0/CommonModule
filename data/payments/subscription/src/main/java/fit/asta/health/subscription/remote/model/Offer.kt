package fit.asta.health.subscription.remote.model


import com.google.gson.annotations.SerializedName

typealias OfferUnit = String
data class Offer(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("desc")
    val desc: String = "",
    @SerializedName("endDate")
    val endDate: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("offer")
    val discount: Int = 0,
    @SerializedName("startDate")
    val startDate: String = "",
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("sub")
    val sub: Sub = Sub(),
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("unit")
    val unit: OfferUnit = "",
    @SerializedName("url")
    val url: String = ""
) {
    data class Sub(
        @SerializedName("durType")
        val durType: String = "",
        @SerializedName("subType")
        val subType: String = ""
    )
}

sealed class OfferUnitType(val type: String) {
    data object Percentage : OfferUnitType("%")
    data object Money : OfferUnitType("$")
}
