package fit.asta.health.data.orders.remote.model


import com.google.gson.annotations.SerializedName

data class OrderDetailData(
    @SerializedName("amt")
    val amt: Double = 0.0,
    @SerializedName("paid")
    val paid: Double = 0.0,
    @SerializedName("dct")
    val couponDiscount: Double? = 0.0,
    @SerializedName("ofr")
    val offerDiscount: Double? = 0.0,
    @SerializedName("wMny")
    val walletMoney: Double? = 0.0,
    @SerializedName("wPts")
    val walletPoints: Double? = 0.0,
    @SerializedName("mode")
    val paymentMode: String? = null,
    @SerializedName("cDate")
    val cDate: Long = 0,
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
)