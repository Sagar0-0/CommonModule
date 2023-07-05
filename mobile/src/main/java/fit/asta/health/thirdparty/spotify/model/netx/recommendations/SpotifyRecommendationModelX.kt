package fit.asta.health.thirdparty.spotify.model.netx.recommendations


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.TrackX
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyRecommendationModelX(
    @SerializedName("seeds")
    val seeds: List<SeedX>,
    @SerializedName("tracks")
    val trackList: List<TrackX>
) : Parcelable