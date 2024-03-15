package fit.asta.health.data.spotify.remote.model.saved

import com.google.gson.annotations.SerializedName
import fit.asta.health.data.spotify.remote.model.common.Track

data class TrackParent(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("track")
    val track: Track
)