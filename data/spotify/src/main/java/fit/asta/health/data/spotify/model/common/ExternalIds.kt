package fit.asta.health.data.spotify.model.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalIds(
    @SerializedName("isrc")
    val isrc: String
) : Parcelable