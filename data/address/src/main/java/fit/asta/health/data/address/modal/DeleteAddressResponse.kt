package fit.asta.health.data.address.modal


import com.google.gson.annotations.SerializedName

data class DeleteAddressResponse(
    @SerializedName("flag")
    val flag: Boolean = false,
    @SerializedName("status")
    val status: Status = Status()
) {
    data class Status(
        @SerializedName("code")
        val code: Int=0,
        @SerializedName("msg")
        val msg: String=""
    )
}