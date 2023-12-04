package fit.asta.health.data.orders.remote.model


import com.google.gson.annotations.SerializedName


data class OrderData(
    @SerializedName("amt")
    val amt: Int = 0,
    @SerializedName("cDate")
    val cDate: Int = 0,
    @SerializedName("durType")
    val durType: String = "",
    @SerializedName("orderId")
    val orderId: String = "",
    @SerializedName("paymentId")
    val paymentId: String = "",
    @SerializedName("subType")
    val subType: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("ttl")
    val title: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("sts")
    val status: String = "",
)