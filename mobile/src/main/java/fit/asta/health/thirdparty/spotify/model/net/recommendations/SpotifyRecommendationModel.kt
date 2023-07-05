package fit.asta.health.thirdparty.spotify.model.net.recommendations


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyRecommendationModel(
    @SerializedName("seeds")
    val seeds: List<Seed>,
    @SerializedName("tracks")
    val trackList: List<Track>
) : Parcelable