package fit.asta.health.thirdparty.spotify.model

import fit.asta.health.thirdparty.spotify.model.api.SpotifyApi
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
import javax.inject.Inject
import javax.inject.Named

class SpotifyRepoImpl @Inject constructor(
    @Named("SPOTIFY")
    private val spotifyApi: SpotifyApi
) : SpotifyRepo {
    override suspend fun getCurrentUserDetails(accessToken: String): Response<SpotifyMeModel> {
        return spotifyApi.getCurrentUserDetails(accessToken = accessToken)
    }

    override suspend fun getCurrentUserFollowedArtists(accessToken: String): Response<SpotifyUserFollowingArtist> {
        return spotifyApi.getCurrentUserFollowedArtists(accessToken = accessToken)
    }

    override suspend fun getCurrentUserTopTracks(accessToken: String): Response<TrackList> {
        return spotifyApi.getCurrentUserTopTracks(accessToken = accessToken)
    }

    override suspend fun getCurrentUserTopArtists(accessToken: String): Response<ArtistList> {
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

    override suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String): Response<SpotifyUserRecentlyPlayedModel> {
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
    ): Response<Track> {
        return spotifyApi.getTrackDetails(
            accessToken = accessToken,
            trackID = trackID
        )
    }

    override suspend fun getAlbumDetails(
        accessToken: String,
        albumID: String
    ): Response<Album> {
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
    ): Response<SpotifySearchModel> {
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
    ): Response<SpotifyRecommendationModel> {
        return spotifyApi.getRecommendations(
            accessToken = accessToken,
            seedArtists = seedArtists,
            seedGenres = seedGenres,
            seedTracks = seedTracks,
            limit = limit
        )
    }
}