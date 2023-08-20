package com.example.payment.model

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("status")
    val status: Status = Status()
) {
    data class Status(
        @SerializedName("code")
        val code: Int = 0,
        @SerializedName("msg")
        val msg: String = ""
    )
}
