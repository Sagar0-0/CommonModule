package fit.asta.health.thirdparty.spotify.model.api

import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.model.net.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.model.net.library.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.model.net.library.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.model.net.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.thirdparty.spotify.model.net.library.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.model.net.library.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.model.net.me.SpotifyMeModel
import fit.asta.health.thirdparty.spotify.model.net.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.thirdparty.spotify.model.net.recommendations.SpotifyRecommendationModel
import fit.asta.health.thirdparty.spotify.model.net.search.ArtistList
import fit.asta.health.thirdparty.spotify.model.net.search.SpotifySearchModel
import fit.asta.health.thirdparty.spotify.model.net.search.TrackList
import okhttp3.OkHttpClient
import retrofit2.Response
import javax.inject.Inject

class SpotifyRestImpl @Inject constructor(baseUrl: String, client: OkHttpClient) : SpotifyApi {

    private val spotifyApiService: SpotifyApiService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(SpotifyApiService::class.java)

    override suspend fun getCurrentUserDetails(accessToken: String): Response<SpotifyMeModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserDetails(headerMap)
    }

    override suspend fun getCurrentUserFollowedArtists(accessToken: String): Response<SpotifyUserFollowingArtist> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserFollowedArtists(headerMap)
    }

    override suspend fun getCurrentUserTopTracks(accessToken: String): Response<TrackList> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserTopTracks(headerMap)
    }

    override suspend fun getCurrentUserTopArtists(accessToken: String): Response<ArtistList> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserTopArtists(headerMap)
    }

    override suspend fun getCurrentUserAlbums(
        accessToken: String
    ): Response<SpotifyLibraryAlbumModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserAlbums(headerMap)
    }

    override suspend fun getCurrentUserShows(
        accessToken: String
    ): Response<SpotifyLibraryShowsModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserShows(headerMap)
    }

    override suspend fun getCurrentUserEpisodes(
        accessToken: String
    ): Response<SpotifyLibraryEpisodesModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserEpisodes(headerMap)
    }

    override suspend fun getCurrentUserTracks(
        accessToken: String
    ): Response<SpotifyLibraryTracksModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserTracks(headerMap)
    }

    override suspend fun getCurrentUserPlaylists(
        accessToken: String
    ): Response<SpotifyUserPlaylistsModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserPlaylists(headerMap)
    }

    override suspend fun getCurrentUserRecentlyPlayedTracks(
        accessToken: String
    ): Response<SpotifyUserRecentlyPlayedModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        val queryMap: HashMap<String, String> = HashMap()
        return spotifyApiService.getCurrentUserRecentlyPlayedTracks(headerMap, queryMap)
    }

    override suspend fun getUserPlaylists(
        accessToken: String,
        userID: String
    ): Response<SpotifyUserPlaylistsModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getUserPlaylists(headerMap, userID)
    }

    override suspend fun getTrackDetails(
        accessToken: String,
        trackID: String
    ): Response<Track> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getTrackDetails(headerMap, trackID)
    }

    override suspend fun getAlbumDetails(
        accessToken: String,
        albumID: String
    ): Response<Album> {
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
    ): Response<SpotifySearchModel> {
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
    ): Response<SpotifyRecommendationModel> {
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