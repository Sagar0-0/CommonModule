package fit.asta.health.scheduler.model

import fit.asta.health.scheduler.model.net.spotify.me.SpotifyMeModel
import fit.asta.health.scheduler.model.net.spotify.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.scheduler.model.net.spotify.search.SpotifySearchModel
import fit.asta.health.scheduler.model.net.spotify.search.TrackList
import fit.asta.health.scheduler.util.SpotifyNetworkCall
import kotlinx.coroutines.flow.Flow

interface SpotifyRepo {

    suspend fun getCurrentUserDetails(accessToken: String): Flow<SpotifyNetworkCall<SpotifyMeModel>>

    suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String): Flow<SpotifyNetworkCall<SpotifyUserRecentlyPlayedModel>>

    suspend fun getCurrentUserTopTracks(accessToken: String): Flow<SpotifyNetworkCall<TrackList>>

    suspend fun searchQuery(
        accessToken: String,
        query: String,
        type: String,
        includeExternal: String,
        market: String
    ): Flow<SpotifyNetworkCall<SpotifySearchModel>>

}