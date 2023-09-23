package fit.asta.health.data.address.remote.modal

import android.location.Address
import com.google.gson.annotations.SerializedName
import fit.asta.health.common.utils.getShortAddressName

data class MyAddress(
    @SerializedName("cl")
    val selected: Boolean = false,
    @SerializedName("area")
    val area: String = "",
    @SerializedName("block")
    val block: String = "",
    @SerializedName("hn")
    val hn: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("loc")
    val loc: String = "",
    @SerializedName("lon")
    val lon: Double = 0.0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("nearby")
    val nearby: String = "",
    @SerializedName("ph")
    val ph: String = "",
    @SerializedName("pin")
    val pin: String = "",
    @SerializedName("sub")
    val sub: String = "",
    @SerializedName("uid")
    val uid: String = "",
    val addressLine: String = "",
    val shortAddress: String = ""
)

fun Address.mapToMyAddress(): MyAddress {
    val items = this.getAddressLine(0).split(", ")
    return MyAddress(
        selected = true,
        area = this.adminArea,
        block = items[1],
        hn = items[0],
        id = "",
        lat = this.latitude,
        loc = this.locality,
        lon = this.longitude,
        name = "",
        nearby = "",
        ph = "",
        pin = this.postalCode,
        sub = this.subLocality ?: this.locality,
        addressLine = this.getAddressLine(0),
        shortAddress = this.getShortAddressName()
    )
}