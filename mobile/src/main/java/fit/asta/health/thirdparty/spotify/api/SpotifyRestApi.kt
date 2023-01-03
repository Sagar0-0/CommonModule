package fit.asta.health.thirdparty.spotify.api

import android.util.Log
import fit.asta.health.thirdparty.spotify.model.net.albums.SpotifyAlbumDetailsModel
import fit.asta.health.thirdparty.spotify.model.net.categories.SpotifyBrowseCategoriesModel
import fit.asta.health.thirdparty.spotify.model.net.me.SpotifyMeModel
import fit.asta.health.thirdparty.spotify.model.net.me.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.model.net.me.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.model.net.me.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.model.net.me.player.recentlyplayed.SpotifyPlayerRecentlyPlayedModel
import fit.asta.health.thirdparty.spotify.model.net.me.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.model.net.me.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.model.net.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.thirdparty.spotify.model.net.recommendations.SpotifyRecommendationModel
import fit.asta.health.thirdparty.spotify.model.net.search.SpotifySearchModel
import fit.asta.health.thirdparty.spotify.model.net.top.SpotifyTopArtistsModel
import fit.asta.health.thirdparty.spotify.model.net.top.SpotifyTopTracksModel
import fit.asta.health.thirdparty.spotify.model.net.tracks.SpotifyTrackDetailsModel
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.TAG
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class SpotifyRestApi @Inject constructor(
    @Named("SPOTIFY")
    private val spotifyApiService: SpotifyApiService
) : SpotifyRemoteApis {
    override suspend fun getCurrentUserDetails(accessToken: String): Response<SpotifyMeModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        Log.d(TAG, "getCurrentUserDetails: $headerMap")
        return spotifyApiService.getCurrentUserDetails(headerMap)
    }

    override suspend fun getCurrentUserFollowedArtists(accessToken: String): Response<SpotifyUserFollowingArtist> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        Log.d(TAG, "getCurrentUserFollowedArtists: $headerMap")
        return spotifyApiService.getCurrentUserFollowedArtists(headerMap)
    }

    override suspend fun getCurrentUserTopTracks(accessToken: String): Response<SpotifyTopTracksModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        Log.d(TAG, "getCurrentUserTopTracks: $headerMap")
        return spotifyApiService.getCurrentUserTopTracks(headerMap)
    }

    override suspend fun getCurrentUserTopArtists(accessToken: String): Response<SpotifyTopArtistsModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        Log.d(TAG, "getCurrentUserTopArtists: $headerMap")
        return spotifyApiService.getCurrentUserTopArtists(headerMap)
    }

    override suspend fun getCurrentUserAlbums(
        accessToken: String
    ): Response<SpotifyLibraryAlbumModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        Log.d(TAG, "getCurrentUserPlaylists: $headerMap")
        return spotifyApiService.getCurrentUserAlbums(headerMap)
    }

    override suspend fun getCurrentUserShows(
        accessToken: String
    ): Response<SpotifyLibraryShowsModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        Log.d(TAG, "getCurrentUserPlaylists: $headerMap")
        return spotifyApiService.getCurrentUserShows(headerMap)
    }

    override suspend fun getCurrentUserEpisodes(
        accessToken: String
    ): Response<SpotifyLibraryEpisodesModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        Log.d(TAG, "getCurrentUserPlaylists: $headerMap")
        return spotifyApiService.getCurrentUserEpisodes(headerMap)
    }

    override suspend fun getCurrentUserTracks(
        accessToken: String
    ): Response<SpotifyLibraryTracksModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        Log.d(TAG, "getCurrentUserPlaylists: $headerMap")
        return spotifyApiService.getCurrentUserTracks(headerMap)
    }

    override suspend fun getCurrentUserPlaylists(
        accessToken: String
    ): Response<SpotifyUserPlaylistsModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        Log.d(TAG, "getCurrentUserPlaylists: $headerMap")
        return spotifyApiService.getCurrentUserPlaylists(headerMap)
    }

    override suspend fun getCurrentUserRecentlyPlayedTracks(
        accessToken: String
    ): Response<SpotifyPlayerRecentlyPlayedModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        val queryMap: HashMap<String, String> = HashMap()
        Log.d(TAG, "getCurrentUserPlaylists: $headerMap")
        return spotifyApiService.getCurrentUserRecentlyPlayedTracks(headerMap, queryMap)
    }

    override suspend fun getUserPlaylists(
        accessToken: String,
        userID: String
    ): Response<SpotifyUserPlaylistsModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        Log.d(TAG, "getUserPlaylists: $headerMap")
        return spotifyApiService.getUserPlaylists(headerMap, userID)
    }

    override suspend fun getTrackDetails(
        accessToken: String,
        trackID: String
    ): Response<SpotifyTrackDetailsModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        Log.d(TAG, "getTrackDetails: $headerMap")
        return spotifyApiService.getTrackDetails(headerMap, trackID)
    }

    override suspend fun getAlbumDetails(
        accessToken: String,
        albumID: String
    ): Response<SpotifyAlbumDetailsModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        Log.d(TAG, "getTrackDetails: $headerMap")
        return spotifyApiService.getAlbumDetails(headerMap, albumID)
    }

    override suspend fun getCategories(
        accessToken: String,
        country: String
    ): Response<SpotifyBrowseCategoriesModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        val queryMap: HashMap<String, String> = HashMap()
        queryMap["country"] = country
        Log.d(TAG, "getCategories: $headerMap $queryMap")
        return spotifyApiService.getCategories(headerMap, queryMap)
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
        Log.d(TAG, "getCategories: $headerMap $queryMap")
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
        Log.d(TAG, "getRecommendations: $headerMap $queryMap")
        return spotifyApiService.getRecommendations(headerMap, queryMap)
    }
}