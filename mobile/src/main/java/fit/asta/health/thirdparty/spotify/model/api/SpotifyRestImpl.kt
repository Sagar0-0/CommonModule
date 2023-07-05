package fit.asta.health.thirdparty.spotify.model.api

import fit.asta.health.thirdparty.spotify.model.netx.common.AlbumX
import fit.asta.health.thirdparty.spotify.model.netx.library.albums.SpotifyLibraryAlbumModelX
import fit.asta.health.thirdparty.spotify.model.netx.library.episodes.SpotifyLibraryEpisodesModelX
import fit.asta.health.thirdparty.spotify.model.netx.library.following.SpotifyUserFollowingArtistX
import fit.asta.health.thirdparty.spotify.model.netx.library.shows.SpotifyLibraryShowsModelX
import fit.asta.health.thirdparty.spotify.model.netx.library.tracks.SpotifyLibraryTracksModelX
import fit.asta.health.thirdparty.spotify.model.netx.library.playlist.SpotifyUserPlaylistsModelX
import fit.asta.health.thirdparty.spotify.model.netx.search.SpotifySearchModelX
import fit.asta.health.thirdparty.spotify.model.netx.search.ArtistListX
import fit.asta.health.thirdparty.spotify.model.netx.search.TrackListX
import fit.asta.health.thirdparty.spotify.model.netx.common.TrackX
import fit.asta.health.thirdparty.spotify.model.netx.me.SpotifyMeModelX
import fit.asta.health.thirdparty.spotify.model.netx.recently.SpotifyUserRecentlyPlayedModelX
import fit.asta.health.thirdparty.spotify.model.netx.recommendations.SpotifyRecommendationModelX
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class SpotifyRestImpl @Inject constructor(
    @Named("SPOTIFY")
    private val spotifyApiService: SpotifyApiService
) : SpotifyApi {
    override suspend fun getCurrentUserDetails(accessToken: String): Response<SpotifyMeModelX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserDetails(headerMap)
    }

    override suspend fun getCurrentUserFollowedArtists(accessToken: String): Response<SpotifyUserFollowingArtistX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserFollowedArtists(headerMap)
    }

    override suspend fun getCurrentUserTopTracks(accessToken: String): Response<TrackListX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserTopTracks(headerMap)
    }

    override suspend fun getCurrentUserTopArtists(accessToken: String): Response<ArtistListX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserTopArtists(headerMap)
    }

    override suspend fun getCurrentUserAlbums(
        accessToken: String
    ): Response<SpotifyLibraryAlbumModelX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserAlbums(headerMap)
    }

    override suspend fun getCurrentUserShows(
        accessToken: String
    ): Response<SpotifyLibraryShowsModelX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserShows(headerMap)
    }

    override suspend fun getCurrentUserEpisodes(
        accessToken: String
    ): Response<SpotifyLibraryEpisodesModelX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserEpisodes(headerMap)
    }

    override suspend fun getCurrentUserTracks(
        accessToken: String
    ): Response<SpotifyLibraryTracksModelX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserTracks(headerMap)
    }

    override suspend fun getCurrentUserPlaylists(
        accessToken: String
    ): Response<SpotifyUserPlaylistsModelX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserPlaylists(headerMap)
    }

    override suspend fun getCurrentUserRecentlyPlayedTracks(
        accessToken: String
    ): Response<SpotifyUserRecentlyPlayedModelX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        val queryMap: HashMap<String, String> = HashMap()
        return spotifyApiService.getCurrentUserRecentlyPlayedTracks(headerMap, queryMap)
    }

    override suspend fun getUserPlaylists(
        accessToken: String,
        userID: String
    ): Response<SpotifyUserPlaylistsModelX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getUserPlaylists(headerMap, userID)
    }

    override suspend fun getTrackDetails(
        accessToken: String,
        trackID: String
    ): Response<TrackX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getTrackDetails(headerMap, trackID)
    }

    override suspend fun getAlbumDetails(
        accessToken: String,
        albumID: String
    ): Response<AlbumX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getAlbumDetails(headerMap, albumID)
    }

    override suspend fun searchQuery(
        accessToken: String,
        query: String,
        type: String,
        includeExternal: String,
        market: String
    ): Response<SpotifySearchModelX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        val queryMap: HashMap<String, String> = HashMap()
        queryMap["q"] = query
        queryMap["type"] = type
        queryMap["include_external"] = includeExternal
        queryMap["market"] = market
        return spotifyApiService.searchQuery(headerMap, queryMap)
    }

    override suspend fun getRecommendations(
        accessToken: String,
        seedArtists: String,
        seedGenres: String,
        seedTracks: String,
        limit: String
    ): Response<SpotifyRecommendationModelX> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        val queryMap: HashMap<String, String> = HashMap()
        queryMap["seed_artists"] = seedArtists
        queryMap["seed_genres"] = seedGenres
        queryMap["seed_tracks"] = seedTracks
        queryMap["limit"] = limit
        return spotifyApiService.getRecommendations(headerMap, queryMap)
    }
}