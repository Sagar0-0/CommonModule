package fit.asta.health.common.address.data.modal


import com.google.gson.annotations.SerializedName

data class DeleteAddressResponse(
    @SerializedName("flag")
    val flag: Boolean,
    @SerializedName("status")
    val status: Status
) {
    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("msg")
        val msg: String
    )
}