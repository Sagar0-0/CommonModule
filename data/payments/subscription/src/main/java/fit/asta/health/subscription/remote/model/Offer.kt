package fit.asta.health.subscription.remote.model


import com.google.gson.annotations.SerializedName



data class Offer(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("dsc")
    val desc: String = "",
    @SerializedName("end")
    val endDate: Long = 0L,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("offer")
    val discount: Int = 0,
    @SerializedName("start")
    val startDate: Long = 0L,
    @SerializedName("sts")
    val status: Int = 0,
    @SerializedName("ttl")
    val title: String = "",
    @SerializedName("unit")
    val unit: Int = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("type")
    val type: Int = 0,
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