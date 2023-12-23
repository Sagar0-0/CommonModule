package fit.asta.health.data.scheduler.remote.net.tag


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TagData(
    @SerializedName("id")
    val id: String, // 633eac4769e8a8a7f88421d8
    @SerializedName("name")
    val name: String, // Breathing
    @SerializedName("dsc")
    var dsc: String = "",
    @SerializedName("ttl")
    var ttl: String = "",
    @SerializedName("uid")
    val uid: String, // 000000000000000000000000
    @SerializedName("url")
    val url: String // /tags/Breathing+Tag.png
) : Parcelable