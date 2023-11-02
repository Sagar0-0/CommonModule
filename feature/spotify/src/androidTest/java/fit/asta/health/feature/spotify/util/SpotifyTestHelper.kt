package fit.asta.health.feature.spotify.util

import fit.asta.health.data.spotify.model.common.Album
import fit.asta.health.data.spotify.model.common.Artist
import fit.asta.health.data.spotify.model.common.ExternalIds
import fit.asta.health.data.spotify.model.common.ExternalUrls
import fit.asta.health.data.spotify.model.common.Followers
import fit.asta.health.data.spotify.model.common.Image
import fit.asta.health.data.spotify.model.common.Track
import fit.asta.health.data.spotify.model.search.AlbumList
import fit.asta.health.data.spotify.model.search.ArtistList
import fit.asta.health.data.spotify.model.search.EpisodeList
import fit.asta.health.data.spotify.model.search.PlaylistList
import fit.asta.health.data.spotify.model.search.ShowList
import fit.asta.health.data.spotify.model.search.SpotifySearchModel
import fit.asta.health.data.spotify.model.search.TrackList

object SpotifyTestHelper {
    fun albumGenerator(): Album {
        return Album(
            albumType = "Test Type",
            artists = listOf(
                Artist(
                    externalUrls = ExternalUrls(spotify = ""),
                    followers = Followers(href = "", total = 10),
                    genres = listOf(""),
                    href = "",
                    id = "",
                    images = listOf(
                        Image(height = 10, url = "def.url", width = 20)
                    ),
                    name = "",
                    popularity = 10,
                    type = "",
                    uri = ""
                )
            ),
            availableMarkets = listOf(""),
            externalUrls = ExternalUrls(spotify = ""),
            href = "",
            id = "",
            images = listOf(
                Image(height = 10, url = "abc.url", width = 20)
            ),
            name = "Test Name",
            releaseDate = "",
            releaseDatePrecision = "",
            totalTracks = 10,
            type = "",
            uri = "test uri"
        )
    }

    fun multipleAlbumGenerator(count: Int = 10): List<Album> {
        val albumList: MutableList<Album> = mutableListOf()

        for (i in 1..count) {
            albumList.add(albumGenerator())
        }
        return albumList
    }

    fun trackGenerator(): Track {
        return Track(
            album = albumGenerator(),
            artists = listOf(),
            availableMarkets = null,
            discNumber = 2,
            durationMs = 2,
            explicit = false,
            externalUrls = ExternalUrls(spotify = ""),
            externalIds = ExternalIds(isrc = ""),
            href = "",
            id = "",
            isLocal = false,
            name = "Test Name",
            popularity = 20,
            previewUrl = null,
            trackNumber = 10,
            type = "",
            uri = ""
        )
    }

    fun multipleTrackGenerator(count: Int = 10): List<Track> {
        val trackList: MutableList<Track> = mutableListOf()

        for (i in 1..count) {
            trackList.add(trackGenerator())
        }
        return trackList
    }

    fun generateSpotifySearchState(): SpotifySearchModel {
        return SpotifySearchModel(
            albums = AlbumList(
                href = "",
                albumItems = multipleAlbumGenerator(),
                limit = 10,
                next = "",
                offset = 10,
                previous = null,
                total = 10
            ),
            artists = ArtistList(
                href = "",
                artistList = listOf(),
                limit = 10,
                next = "",
                offset = 10,
                previous = null,
                total = 10
            ),
            episodes = EpisodeList(
                href = "",
                items = listOf(),
                limit = 10,
                next = "",
                offset = 10,
                previous = null,
                total = 10
            ),
            playlists = PlaylistList(
                href = "",
                items = listOf(),
                limit = 10,
                next = "",
                offset = 10,
                previous = null,
                total = 10
            ),
            shows = ShowList(
                href = "",
                items = listOf(),
                limit = 10,
                next = "",
                offset = 10,
                previous = null,
                total = 10
            ),
            tracks = TrackList(
                href = "",
                trackList = multipleTrackGenerator(),
                limit = 10,
                next = "",
                offset = 10,
                previous = null,
                total = 10
            )
        )
    }
}