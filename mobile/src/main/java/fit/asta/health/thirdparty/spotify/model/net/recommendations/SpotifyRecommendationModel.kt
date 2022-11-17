package fit.asta.health.thirdparty.spotify.model.net.recommendations


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyRecommendationModel(
    @SerializedName("seeds")
    val seeds: List<Seed>,
    @SerializedName("tracks")
    val tracks: List<Track>
) : Parcelable