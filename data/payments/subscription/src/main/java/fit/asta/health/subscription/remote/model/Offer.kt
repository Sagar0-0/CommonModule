package fit.asta.health.subscription.remote.model


import com.google.gson.annotations.SerializedName

typealias OfferUnit = Int
data class Offer(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("dsc")
    val desc: String = "",
    @SerializedName("end")
    val endDate: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("offer")
    val discount: Int = 0,
    @SerializedName("start")
    val startDate: String = "",
    @SerializedName("sts")
    val status: Int = 0,
    @SerializedName("ttl")
    val title: String = "",
    @SerializedName("unit")
    val unit: OfferUnit = OfferUnitType.PERCENTAGE.type,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("areas")
    val areas: List<Areas> = listOf()
) {
    data class Areas(
        @SerializedName("id")
        val id: String = "",
        @SerializedName("type")
        val type: String = "",
        @SerializedName("name")
        val name: String = ""
    )
}

fun OfferUnit.getOfferUnitType(): OfferUnitType = OfferUnitType.entries.first { this == it.type }

enum class OfferUnitType(val type: OfferUnit) {
    PERCENTAGE(18),
    RUPEE(23)
}
