package fit.asta.health.thirdparty.spotify.data.model.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalIds(
    @SerializedName("isrc")
    val isrc: String
) : Parcelable