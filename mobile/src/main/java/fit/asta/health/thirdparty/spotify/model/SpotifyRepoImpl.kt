package fit.asta.health.thirdparty.spotify.model

import fit.asta.health.thirdparty.spotify.model.api.SpotifyApi
import fit.asta.health.thirdparty.spotify.model.netx.albums.SpotifyAlbumDetailsModelX
import fit.asta.health.thirdparty.spotify.model.net.categories.SpotifyBrowseCategoriesModel
import fit.asta.health.thirdparty.spotify.model.net.me.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.model.net.me.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.model.net.me.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.model.net.me.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.model.net.me.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.model.net.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.thirdparty.spotify.model.netx.search.SpotifySearchModelX
import fit.asta.health.thirdparty.spotify.model.netx.top.SpotifyTopArtistsModelX
import fit.asta.health.thirdparty.spotify.model.netx.top.SpotifyTopTracksModelX
import fit.asta.health.thirdparty.spotify.model.netx.common.TrackX
import fit.asta.health.thirdparty.spotify.model.netx.me.SpotifyMeModelX
import fit.asta.health.thirdparty.spotify.model.netx.recently.SpotifyPlayerRecentlyPlayedModelX
import fit.asta.health.thirdparty.spotify.model.netx.recommendations.SpotifyRecommendationModelX
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class SpotifyRepoImpl @Inject constructor(
    @Named("SPOTIFY")
    private val spotifyApi: SpotifyApi
) : SpotifyRepo {
    override suspend fun getCurrentUserDetails(accessToken: String): Response<SpotifyMeModelX> {
        return spotifyApi.getCurrentUserDetails(accessToken = accessToken)
    }

    override suspend fun getCurrentUserFollowedArtists(accessToken: String): Response<SpotifyUserFollowingArtist> {
        return spotifyApi.getCurrentUserFollowedArtists(accessToken = accessToken)
    }

    override suspend fun getCurrentUserTopTracks(accessToken: String): Response<SpotifyTopTracksModelX> {
        return spotifyApi.getCurrentUserTopTracks(accessToken = accessToken)
    }

    override suspend fun getCurrentUserTopArtists(accessToken: String): Response<SpotifyTopArtistsModelX> {
        return spotifyApi.getCurrentUserTopArtists(accessToken = accessToken)
    }

    override suspend fun getCurrentUserAlbums(accessToken: String): Response<SpotifyLibraryAlbumModel> {
        return spotifyApi.getCurrentUserAlbums(accessToken = accessToken)
    }

    override suspend fun getCurrentUserShows(accessToken: String): Response<SpotifyLibraryShowsModel> {
        return spotifyApi.getCurrentUserShows(accessToken = accessToken)
    }

    override suspend fun getCurrentUserEpisodes(accessToken: String): Response<SpotifyLibraryEpisodesModel> {
        return spotifyApi.getCurrentUserEpisodes(accessToken = accessToken)
    }

    override suspend fun getCurrentUserTracks(accessToken: String): Response<SpotifyLibraryTracksModel> {
        return spotifyApi.getCurrentUserTracks(accessToken = accessToken)
    }

    override suspend fun getCurrentUserPlaylists(accessToken: String): Response<SpotifyUserPlaylistsModel> {
        return spotifyApi.getCurrentUserPlaylists(accessToken = accessToken)
    }

    override suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String): Response<SpotifyPlayerRecentlyPlayedModelX> {
        return spotifyApi.getCurrentUserRecentlyPlayedTracks(accessToken = accessToken)
    }

    override suspend fun getUserPlaylists(
        accessToken: String,
        userID: String
    ): Response<SpotifyUserPlaylistsModel> {
        return spotifyApi.getUserPlaylists(
            accessToken = accessToken,
            userID = userID
        )
    }

    override suspend fun getTrackDetails(
        accessToken: String,
        trackID: String
    ): Response<TrackX> {
        return spotifyApi.getTrackDetails(
            accessToken = accessToken,
            trackID = trackID
        )
    }

    override suspend fun getAlbumDetails(
        accessToken: String,
        albumID: String
    ): Response<SpotifyAlbumDetailsModelX> {
        return spotifyApi.getAlbumDetails(
            accessToken = accessToken,
            albumID = albumID
        )
    }

    override suspend fun getCategories(
        accessToken: String,
        country: String
    ): Response<SpotifyBrowseCategoriesModel> {
        return spotifyApi.getCategories(
            accessToken = accessToken,
            country = country
        )
    }

    override suspend fun searchQuery(
        accessToken: String,
        query: String,
        type: String,
        includeExternal: String,
        market: String
    ): Response<SpotifySearchModelX> {
        return spotifyApi.searchQuery(
            accessToken = accessToken,
            query = query,
            type = type,
            includeExternal = includeExternal,
            market = market
        )
    }

    override suspend fun getRecommendations(
        accessToken: String,
        seedArtists: String,
        seedGenres: String,
        seedTracks: String,
        limit: String
    ): Response<SpotifyRecommendationModelX> {
        return spotifyApi.getRecommendations(
            accessToken = accessToken,
            seedArtists = seedArtists,
            seedGenres = seedGenres,
            seedTracks = seedTracks,
            limit = limit
        )
    }
}