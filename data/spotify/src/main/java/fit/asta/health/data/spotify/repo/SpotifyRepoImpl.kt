package fit.asta.health.data.spotify.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.data.spotify.remote.SpotifyApi
import fit.asta.health.data.spotify.remote.model.common.Album
import fit.asta.health.data.spotify.remote.model.common.Track
import fit.asta.health.data.spotify.remote.model.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.data.spotify.remote.model.library.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.data.spotify.remote.model.library.following.SpotifyUserFollowingArtist
import fit.asta.health.data.spotify.remote.model.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.data.spotify.remote.model.library.shows.SpotifyLibraryShowsModel
import fit.asta.health.data.spotify.remote.model.library.tracks.SpotifyLibraryTracksModel
import fit.asta.health.data.spotify.remote.model.me.SpotifyMeModel
import fit.asta.health.data.spotify.remote.model.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.data.spotify.remote.model.recommendations.SpotifyRecommendationModel
import fit.asta.health.data.spotify.remote.model.saved.SpotifyLikedSongsResponse
import fit.asta.health.data.spotify.remote.model.search.ArtistList
import fit.asta.health.data.spotify.remote.model.search.SpotifySearchModel
import fit.asta.health.data.spotify.remote.model.search.TrackList
import fit.asta.health.data.spotify.utils.getBearerToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SpotifyRepoImpl
    @Inject constructor(
    private val spotifyApi: SpotifyApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SpotifyRepo {

    override suspend fun getCurrentUserDetails(accessToken: String):
            ResponseState<SpotifyMeModel> {

        return withContext(dispatcher) {
            getResponseState {
                spotifyApi.getCurrentUserDetails(getBearerToken(accessToken))
            }
        }
    }

    override suspend fun getCurrentUserFollowedArtists(accessToken: String):
            ResponseState<SpotifyUserFollowingArtist> {

        return withContext(dispatcher) {
            getResponseState {
                spotifyApi.getCurrentUserFollowedArtists(getBearerToken(accessToken))
            }
        }
    }

    override suspend fun getCurrentUserTopTracks(accessToken: String):
            ResponseState<TrackList> {
        return withContext(dispatcher) {
            getResponseState {
                spotifyApi.getCurrentUserTopTracks(getBearerToken(accessToken))
            }
        }
    }

    override suspend fun getCurrentUserTopArtists(accessToken: String):
            ResponseState<ArtistList> {

        return withContext(dispatcher) {
            getResponseState {
                spotifyApi.getCurrentUserTopArtists(getBearerToken(accessToken))
            }
        }
    }

    override suspend fun getCurrentUserAlbums(accessToken: String):
            ResponseState<SpotifyLibraryAlbumModel> {

        return withContext(dispatcher) {
            getResponseState {
                spotifyApi.getCurrentUserAlbums(getBearerToken(accessToken))
            }
        }
    }

    override suspend fun getCurrentUserShows(accessToken: String):
            ResponseState<SpotifyLibraryShowsModel> {

        return withContext(dispatcher) {
            getResponseState {
                spotifyApi.getCurrentUserShows(getBearerToken(accessToken))
            }
        }
    }

    override suspend fun getCurrentUserEpisodes(accessToken: String):
            ResponseState<SpotifyLibraryEpisodesModel> {

        return withContext(dispatcher) {
            getResponseState {
                spotifyApi.getCurrentUserEpisodes(getBearerToken(accessToken))
            }
        }
    }

    override suspend fun getCurrentUserTracks(accessToken: String):
            ResponseState<SpotifyLibraryTracksModel> {

        return withContext(dispatcher) {
            getResponseState {
                spotifyApi.getCurrentUserTracks(getBearerToken(accessToken))
            }
        }
    }

    override suspend fun getCurrentUserPlaylists(accessToken: String):
            ResponseState<SpotifyUserPlaylistsModel> {

        return withContext(dispatcher) {
            getResponseState {
                spotifyApi.getCurrentUserPlaylists(getBearerToken(accessToken))
            }
        }
    }

    override suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String):
            ResponseState<SpotifyUserRecentlyPlayedModel> {

        return withContext(dispatcher) {
            getResponseState {

                val queryMap: HashMap<String, String> = HashMap()

                spotifyApi.getCurrentUserRecentlyPlayedTracks(getBearerToken(accessToken), queryMap)
            }
        }
    }

    override suspend fun getUserPlaylists(accessToken: String, userID: String):
            ResponseState<SpotifyUserPlaylistsModel> {

        return withContext(dispatcher) {
            getResponseState {

                spotifyApi.getUserPlaylists(getBearerToken(accessToken), userID)
            }
        }
    }

    override suspend fun getTrackDetails(accessToken: String, trackID: String):
            ResponseState<Track> {

        return withContext(dispatcher) {
            getResponseState {
                spotifyApi.getTrackDetails(getBearerToken(accessToken), trackID)
            }
        }
    }

    override suspend fun getAlbumDetails(accessToken: String, albumID: String):
            ResponseState<Album> {

        return withContext(dispatcher) {
            getResponseState {
                spotifyApi.getAlbumDetails(getBearerToken(accessToken), albumID)
            }
        }
    }

    override suspend fun searchQuery(
        accessToken: String,
        query: String,
        type: String,
        includeExternal: String,
        market: String
    ): ResponseState<SpotifySearchModel> {

        return withContext(dispatcher) {
            getResponseState {

                val queryMap: HashMap<String, String> = HashMap()
                queryMap["q"] = query
                queryMap["type"] = type
                queryMap["include_external"] = includeExternal
                queryMap["market"] = market

                spotifyApi.searchQuery(getBearerToken(accessToken), queryMap)
            }
        }
    }

    override suspend fun getRecommendations(
        accessToken: String,
        seedArtists: String,
        seedGenres: String,
        seedTracks: String,
        limit: String
    ): ResponseState<SpotifyRecommendationModel> {

        return withContext(dispatcher) {
            getResponseState {
                val queryMap: HashMap<String, String> = HashMap()
                queryMap["seed_artists"] = seedArtists
                queryMap["seed_genres"] = seedGenres
                queryMap["seed_tracks"] = seedTracks
                queryMap["limit"] = limit

                spotifyApi.getRecommendations(getBearerToken(accessToken), queryMap)
            }
        }
    }

    override suspend fun getCurrentUserSavedSongs(
        accessToken: String,
        market: String,
        limit: String,
        offset: String
    ): ResponseState<SpotifyLikedSongsResponse> {

        return withContext(dispatcher) {
            getResponseState {
                val queryMap: HashMap<String, String> = HashMap()
                queryMap["market"] = market
                queryMap["limit"] = limit
                queryMap["offset"] = offset
                spotifyApi.getCurrentUserSavedSongs(getBearerToken(accessToken), queryMap)
            }
        }
    }
}