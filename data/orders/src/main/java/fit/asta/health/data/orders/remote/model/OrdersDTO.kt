package fit.asta.health.data.orders.remote.model


import com.google.gson.annotations.SerializedName

data class OrdersDTO(
    @SerializedName("data")
    val `data`: List<Data> = listOf(),
    @SerializedName("status")
    val status: Status = Status()
) {
    data class Data(
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
        val type: Int = 0
    )

    data class Status(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("msg")
        val msg: String = ""
    )
}