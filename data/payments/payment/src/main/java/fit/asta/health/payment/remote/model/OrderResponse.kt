package fit.asta.health.payment.remote.model


import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("api_key")
    val apiKey: String = "",
    @SerializedName("order_id")
    val orderId: String = ""
)