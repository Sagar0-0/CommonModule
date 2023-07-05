package fit.asta.health.thirdparty.spotify.model.api

import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.model.net.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.model.net.library.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.model.net.library.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.model.net.library.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.model.net.library.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.model.net.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.thirdparty.spotify.model.net.search.SpotifySearchModel
import fit.asta.health.thirdparty.spotify.model.net.search.ArtistList
import fit.asta.health.thirdparty.spotify.model.net.search.TrackList
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.model.net.me.SpotifyMeModel
import fit.asta.health.thirdparty.spotify.model.net.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.thirdparty.spotify.model.net.recommendations.SpotifyRecommendationModel
import retrofit2.Response

interface SpotifyApi {

    suspend fun getCurrentUserDetails(accessToken: String): Response<SpotifyMeModel>

    suspend fun getCurrentUserFollowedArtists(accessToken: String): Response<SpotifyUserFollowingArtist>

    suspend fun getCurrentUserTopTracks(accessToken: String): Response<TrackList>

    suspend fun getCurrentUserTopArtists(accessToken: String): Response<ArtistList>

    suspend fun getCurrentUserAlbums(accessToken: String): Response<SpotifyLibraryAlbumModel>

    suspend fun getCurrentUserShows(accessToken: String): Response<SpotifyLibraryShowsModel>

    suspend fun getCurrentUserEpisodes(accessToken: String): Response<SpotifyLibraryEpisodesModel>

    suspend fun getCurrentUserTracks(accessToken: String): Response<SpotifyLibraryTracksModel>

    suspend fun getCurrentUserPlaylists(accessToken: String): Response<SpotifyUserPlaylistsModel>

    suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String): Response<SpotifyUserRecentlyPlayedModel>

    suspend fun getUserPlaylists(
        accessToken: String,
        userID: String
    ): Response<SpotifyUserPlaylistsModel>

    suspend fun getTrackDetails(
        accessToken: String,
        trackID: String
    ): Response<Track>

    suspend fun getAlbumDetails(
        accessToken: String,
        albumID: String
    ): Response<Album>

    suspend fun searchQuery(
        accessToken: String,
        query: String,
        type: String,
        includeExternal: String,
        market: String
    ): Response<SpotifySearchModel>

    suspend fun getRecommendations(
        accessToken: String,
        seedArtists: String,
        seedGenres: String,
        seedTracks: String,
        limit: String
    ): Response<SpotifyRecommendationModel>
}