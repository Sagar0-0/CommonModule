package fit.asta.health.data.orders.remote.model


import com.google.gson.annotations.SerializedName

data class OrderDetailData(
    @SerializedName("amt")
    val amt: Double = 0.0,
    @SerializedName("mode")
    val paymentMode: String? = null,
    @SerializedName("cDate")
    val cDate: Int = 0,
    @SerializedName("dct")
    val discount: Double? = 0.0,
    @SerializedName("ofr")
    val offer: Double? = 0.0,
    @SerializedName("oid")
    val orderId: String = "",
    @SerializedName("pid")
    val paymentId: String = "",
    @SerializedName("sts")
    val sts: String = "",
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("url")
    val imageUrl: String = "",
    @SerializedName("wMny")
    val walletMoney: Double? = 0.0,
    @SerializedName("wPts")
    val walletPoints: Double? = 0.0
)