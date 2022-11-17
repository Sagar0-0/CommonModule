package fit.asta.health.thirdparty.spotify.model

import fit.asta.health.network.api.SpotifyRemoteApis
import javax.inject.Inject
import javax.inject.Named

class SpotifyRepository @Inject constructor(
    @Named("SPOTIFY")
    spotifyRemoteApis: SpotifyRemoteApis
) {
    val remote = spotifyRemoteApis
}