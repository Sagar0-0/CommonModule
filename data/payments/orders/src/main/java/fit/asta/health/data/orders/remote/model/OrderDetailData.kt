package fit.asta.health.data.orders.remote.model


import com.google.gson.annotations.SerializedName

data class OrderDetailData(
    @SerializedName("amt")
    val amt: Int = 0,
    @SerializedName("cDate")
    val cDate: Int = 0,
    @SerializedName("discount")
    val discount: Int = 0,
    @SerializedName("offer")
    val offer: String = "",
    @SerializedName("orderId")
    val orderId: String = "",
    @SerializedName("paymentId")
    val paymentId: String = "",
    @SerializedName("paymentMode")
    val paymentMode: String = "",
    @SerializedName("sts")
    val sts: String = "",
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("walletMoney")
    val walletMoney: Int = 0,
    @SerializedName("walletPoints")
    val walletPoints: Int = 0
)