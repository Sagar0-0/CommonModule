package fit.asta.health.data.address.remote.modal


import com.google.gson.annotations.SerializedName

data class DeleteAddressResponse(
    @SerializedName("flag")
    val flag: Boolean = false
)