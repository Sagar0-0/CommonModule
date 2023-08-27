package fit.asta.health.data.spotify.model.recommendations


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.data.spotify.model.common.Track
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyRecommendationModel(
    @SerializedName("seeds")
    val seeds: List<Seed>,
    @SerializedName("tracks")
    val trackList: List<Track>
) : Parcelable