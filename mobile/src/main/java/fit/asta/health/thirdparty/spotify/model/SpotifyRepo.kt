package fit.asta.health.thirdparty.spotify.model

import fit.asta.health.thirdparty.spotify.model.net.albums.SpotifyAlbumDetailsModel
import fit.asta.health.thirdparty.spotify.model.net.categories.SpotifyBrowseCategoriesModel
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
import fit.asta.health.thirdparty.spotify.model.netx.me.SpotifyMeModelX
import retrofit2.Response

interface SpotifyRepo {

    suspend fun getCurrentUserDetails(accessToken: String): Response<SpotifyMeModelX>

    suspend fun getCurrentUserFollowedArtists(accessToken: String): Response<SpotifyUserFollowingArtist>

    suspend fun getCurrentUserTopTracks(accessToken: String): Response<SpotifyTopTracksModel>

    suspend fun getCurrentUserTopArtists(accessToken: String): Response<SpotifyTopArtistsModel>

    suspend fun getCurrentUserAlbums(accessToken: String): Response<SpotifyLibraryAlbumModel>

    suspend fun getCurrentUserShows(accessToken: String): Response<SpotifyLibraryShowsModel>

    suspend fun getCurrentUserEpisodes(accessToken: String): Response<SpotifyLibraryEpisodesModel>

    suspend fun getCurrentUserTracks(accessToken: String): Response<SpotifyLibraryTracksModel>

    suspend fun getCurrentUserPlaylists(accessToken: String): Response<SpotifyUserPlaylistsModel>

    suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String): Response<SpotifyPlayerRecentlyPlayedModel>

    suspend fun getUserPlaylists(
        accessToken: String,
        userID: String
    ): Response<SpotifyUserPlaylistsModel>

    suspend fun getTrackDetails(
        accessToken: String,
        trackID: String
    ): Response<SpotifyTrackDetailsModel>

    suspend fun getAlbumDetails(
        accessToken: String,
        albumID: String
    ): Response<SpotifyAlbumDetailsModel>

    suspend fun getCategories(
        accessToken: String,
        country: String
    ): Response<SpotifyBrowseCategoriesModel>

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