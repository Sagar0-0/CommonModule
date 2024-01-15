package fit.asta.health.data.orders.remote.model


import com.google.gson.annotations.SerializedName

data class OrderData(
    @SerializedName("amt")
    val amt: Int = 0,
    @SerializedName("cDate")
    val cDate: Int = 0,
    @SerializedName("oid")
    val orderId: String = "",
    @SerializedName("pid")
    val paymentId: String? = null,
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("ttl")
    val title: String = "",
    @SerializedName("url")
    val imageUrl: String = "",
    @SerializedName("sts")
    val status: String = "",
)