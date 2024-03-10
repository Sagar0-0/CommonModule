package fit.asta.health.data.scheduler.remote.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    @SerializedName("almId")
    val almId: Int, // 1
    @SerializedName("id")
    val id: String, // 6310c0912aeef2b4b3684a6f
    @SerializedName("imp")
    val important: Boolean, // true
    @SerializedName("info")
    val info: Info,
    @SerializedName("ivl")
    val interval: Ivl,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("mode")
    val mode: Int, // 2
    @SerializedName("repeat")
    val repeat: Boolean, // true
    @SerializedName("sts")
    val status: Boolean, // true
    @SerializedName("time")
    val time: Time,
    @SerializedName("tone")
    val tone: Tone,
    @SerializedName("uid")
    val userId: String,
    @SerializedName("vib")
    val vibration: Vib,
    @SerializedName("wk")
    val week: Wk
) : Parcelable