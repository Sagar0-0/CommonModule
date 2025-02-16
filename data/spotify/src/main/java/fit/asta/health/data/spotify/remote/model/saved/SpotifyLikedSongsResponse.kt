package fit.asta.health.data.spotify.remote.model.saved

import com.google.gson.annotations.SerializedName

data class SpotifyLikedSongsResponse(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val trackList: List<TrackParent>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("total")
    val total: Int
)