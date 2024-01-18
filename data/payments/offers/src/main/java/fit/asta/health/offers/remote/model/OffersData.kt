package fit.asta.health.offers.remote.model

import com.google.gson.annotations.SerializedName


typealias OfferUnit = Int

data class OffersData(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("con")
    val country: String = "IND",
    @SerializedName("type")
    val type: OfferUnit = 0,
    @SerializedName("ofr")
    val amountReduced: Int = 0,
    @SerializedName("unit")
    val unit: Int = 0,
    @SerializedName("dsc")
    val desc: String = "",
    @SerializedName("ttl")
    val title: String = "",
    @SerializedName("url")
    val imageUrl: String = "",
    @SerializedName("sts")
    val status: Int = 0,
    @SerializedName("code")
    val offerCode: String = "",
    @SerializedName("start")
    val startDate: String = "",
    @SerializedName("end")
    val endDate: String = "",
    @SerializedName("areas")
    val areas: List<OfferArea> = listOf(),
) {
    data class OfferArea(
        @SerializedName("id")
        val id: String = "",
        @SerializedName("type")
        val type: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("cat")
        val productCategoryId: Int = 0,
        @SerializedName("prod")
        val productId: Int = 0,
    )
}

fun OfferUnit.getOfferUnitType(): OfferUnitType = OfferUnitType.entries.first { this == it.type }

enum class OfferUnitType(val type: OfferUnit) {
    PERCENTAGE(18),
    RUPEE(23)
}