package fit.asta.health.payment.model


import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("status")
    val status: Status = Status()
) {
    data class Data(
        @SerializedName("api_key")
        val apiKey: String = "",
        @SerializedName("order_id")
        val orderId: String = ""
    )

    data class Status(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("msg")
        val msg: String = ""
    )
}