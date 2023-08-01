package fit.asta.health.common.maps.modal


import com.google.gson.annotations.SerializedName

data class AddressesResponse(
    @SerializedName("data")
    val data: List<MyAddress>,
    @SerializedName("status")
    val status: Status
) {
    data class MyAddress(
        @SerializedName("cl")
        val selected: Boolean,
        @SerializedName("area")
        val area: String,
        @SerializedName("block")
        val block: String,
        @SerializedName("hn")
        val hn: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("loc")
        val loc: String,
        @SerializedName("lon")
        val lon: Double,
        @SerializedName("name")
        val name: String,
        @SerializedName("nearby")
        val nearby: String,
        @SerializedName("ph")
        val ph: String,
        @SerializedName("pin")
        val pin: String,
        @SerializedName("sub")
        val sub: String,
        @SerializedName("uid")
        val uid: String
    )

    data class Status(
        @SerializedName("code")
        val code: Int,
        @SerializedName("msg")
        val msg: String
    )
}