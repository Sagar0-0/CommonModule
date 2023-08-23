package fit.asta.health.data.address.modal


import com.google.gson.annotations.SerializedName

data class AddressesDTO(
    @SerializedName("data")
    val data: List<MyAddress> = listOf(),
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