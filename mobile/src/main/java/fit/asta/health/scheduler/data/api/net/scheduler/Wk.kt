package fit.asta.health.scheduler.data.api.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wk(
    @SerializedName("fr")
    val friday: Boolean, // false
    @SerializedName("mo")
    val monday: Boolean, // false
    @SerializedName("sa")
    val saturday: Boolean, // false
    @SerializedName("su")
    val sunday: Boolean, // false
    @SerializedName("th")
    val thursday: Boolean, // false
    @SerializedName("tu")
    val tuesday: Boolean, // false
    @SerializedName("we")
    val wednesday: Boolean, // false
    @SerializedName("repeat")
    val recurring: Boolean
) : Parcelable