package fit.asta.health.thirdparty.spotify.model

import fit.asta.health.thirdparty.spotify.model.api.SpotifyApi
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

    override suspend fun getCurrentUserTopTracks(accessToken: String): Response<TrackListX> {
        return spotifyApi.getCurrentUserTopTracks(accessToken = accessToken)
    }

    override suspend fun getCurrentUserTopArtists(accessToken: String): Response<ArtistListX> {
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

    override suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String): Response<SpotifyUserRecentlyPlayedModelX> {
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
    ): Response<AlbumX> {
        return spotifyApi.getAlbumDetails(
            accessToken = accessToken,
            albumID = albumID
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