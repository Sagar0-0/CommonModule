package fit.asta.health.thirdparty.spotify.model

import fit.asta.health.thirdparty.spotify.model.api.SpotifyApi
import fit.asta.health.thirdparty.spotify.model.netx.albums.SpotifyAlbumDetailsModelX
import fit.asta.health.thirdparty.spotify.model.net.categories.SpotifyBrowseCategoriesModel
import fit.asta.health.thirdparty.spotify.model.netx.me.albums.SpotifyLibraryAlbumModelX
import fit.asta.health.thirdparty.spotify.model.netx.me.episodes.SpotifyLibraryEpisodesModelX
import fit.asta.health.thirdparty.spotify.model.netx.me.following.SpotifyUserFollowingArtistX
import fit.asta.health.thirdparty.spotify.model.netx.me.shows.SpotifyLibraryShowsModelX
import fit.asta.health.thirdparty.spotify.model.netx.me.tracks.SpotifyLibraryTracksModelX
import fit.asta.health.thirdparty.spotify.model.netx.me.playlist.SpotifyUserPlaylistsModelX
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

    override suspend fun getCurrentUserFollowedArtists(accessToken: String): Response<SpotifyUserFollowingArtistX> {
        return spotifyApi.getCurrentUserFollowedArtists(accessToken = accessToken)
    }

    override suspend fun getCurrentUserTopTracks(accessToken: String): Response<SpotifyTopTracksModelX> {
        return spotifyApi.getCurrentUserTopTracks(accessToken = accessToken)
    }

    override suspend fun getCurrentUserTopArtists(accessToken: String): Response<SpotifyTopArtistsModelX> {
        return spotifyApi.getCurrentUserTopArtists(accessToken = accessToken)
    }

    override suspend fun getCurrentUserAlbums(accessToken: String): Response<SpotifyLibraryAlbumModelX> {
        return spotifyApi.getCurrentUserAlbums(accessToken = accessToken)
    }

    override suspend fun getCurrentUserShows(accessToken: String): Response<SpotifyLibraryShowsModelX> {
        return spotifyApi.getCurrentUserShows(accessToken = accessToken)
    }

    override suspend fun getCurrentUserEpisodes(accessToken: String): Response<SpotifyLibraryEpisodesModelX> {
        return spotifyApi.getCurrentUserEpisodes(accessToken = accessToken)
    }

    override suspend fun getCurrentUserTracks(accessToken: String): Response<SpotifyLibraryTracksModelX> {
        return spotifyApi.getCurrentUserTracks(accessToken = accessToken)
    }

    override suspend fun getCurrentUserPlaylists(accessToken: String): Response<SpotifyUserPlaylistsModelX> {
        return spotifyApi.getCurrentUserPlaylists(accessToken = accessToken)
    }

    override suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String): Response<SpotifyPlayerRecentlyPlayedModelX> {
        return spotifyApi.getCurrentUserRecentlyPlayedTracks(accessToken = accessToken)
    }

    override suspend fun getUserPlaylists(
        accessToken: String,
        userID: String
    ): Response<SpotifyUserPlaylistsModelX> {
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