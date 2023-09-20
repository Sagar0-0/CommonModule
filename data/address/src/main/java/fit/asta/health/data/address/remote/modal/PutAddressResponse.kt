package fit.asta.health.data.address.remote.modal


import com.google.gson.annotations.SerializedName

data class PutAddressResponse(
    @SerializedName("flag")
    val flag: Boolean = false,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("msg")
    val msg: String = ""
)