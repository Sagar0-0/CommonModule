package fit.asta.health.common.address.data.modal


import com.google.gson.annotations.SerializedName

data class PutAddressResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
) {
    data class Data(
        @SerializedName("flag")
        val flag: Boolean,
        @SerializedName("id")
        val id: String,
        @SerializedName("msg")
        val msg: String
    )

    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("msg")
        val msg: String
    )
}