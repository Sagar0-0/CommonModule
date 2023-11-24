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

    val title: String = "",//Title for the Order
    val status: String = "",//Status for the Order(Pending/Success/Failed)
    val imgUrl: String = "",//Thumbnail Image for the Order
    val paymentMode: String = "",//PaymentMode for the Order
    val mrp: String = "",//mrp for the Order
    val discount: String = "",//Discount for the Order
    val taxes: String = "",//Taxes for the Order
    val totalAmount: String = "",//Total amount for the Order
    val offersApplied: List<String> = emptyList(),//Applied offers for the Order
)