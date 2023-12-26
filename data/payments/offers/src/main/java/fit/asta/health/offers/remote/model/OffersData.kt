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
    val imageUrl: String = "",
    @SerializedName("sts")
    val status: Int = 0,
    @SerializedName("discount")
    val discount: Int = 0,
    @SerializedName("code")
    val offerCode: String = "",
    @SerializedName("code")
    val offer: String = "",
    @SerializedName("start")
    val startDate: String = "",
    @SerializedName("end")
    val endDate: String = "",
    @SerializedName("unit")
    val unit: String = "",
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
    )
}