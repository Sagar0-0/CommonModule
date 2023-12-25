package fit.asta.health.payment.remote.model

import com.google.gson.annotations.SerializedName

data class PaymentCancelResponse(
    @SerializedName("id")
    val id: String = ""
)
