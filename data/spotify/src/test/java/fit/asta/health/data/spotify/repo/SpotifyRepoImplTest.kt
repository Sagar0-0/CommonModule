package fit.asta.health.data.spotify.repo

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.ResponseState
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
import fit.asta.health.data.spotify.remote.model.search.ArtistList
import fit.asta.health.data.spotify.remote.model.search.SpotifySearchModel
import fit.asta.health.data.spotify.remote.model.search.TrackList
import fit.asta.health.data.spotify.util.JsonReader
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkStatic
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

class SpotifyRepoImplTest {

    private lateinit var spotifyRepoImpl: SpotifyRepoImpl

    @RelaxedMockK
    lateinit var spotifyApi: SpotifyApi
    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        spotifyRepoImpl = spyk(SpotifyRepoImpl(spotifyApi))
    }

    @Nested
    @DisplayName("Get Current User Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserDetails {

        @Test
        fun `getCurrentUserDetails, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/currentUserDetails.json")
            val expectedResponse = gson.fromJson(json, SpotifyMeModel::class.java)

            coEvery { spotifyApi.getCurrentUserDetails(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserDetails("")

            coVerify { spotifyApi.getCurrentUserDetails(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserDetails, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.getCurrentUserDetails(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserDetails("")

            coVerify { spotifyApi.getCurrentUserDetails(any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Current User Followed Artists")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserFollowedArtists {

        @Test
        fun `getCurrentUserFollowedArtists, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserFollowedArtists.json")
            val expectedResponse = gson.fromJson(json, SpotifyUserFollowingArtist::class.java)

            coEvery { spotifyApi.getCurrentUserFollowedArtists(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserFollowedArtists("")

            coVerify { spotifyApi.getCurrentUserFollowedArtists(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserFollowedArtists, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.getCurrentUserFollowedArtists(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserFollowedArtists("")

            coVerify { spotifyApi.getCurrentUserFollowedArtists(any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Current User Top Tracks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserTopTracks {

        @Test
        fun `getCurrentUserTopTracks, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserTopTracks.json")
            val expectedResponse = gson.fromJson(json, TrackList::class.java)

            coEvery { spotifyApi.getCurrentUserTopTracks(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserTopTracks("")

            coVerify { spotifyApi.getCurrentUserTopTracks(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserTopTracks, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.getCurrentUserTopTracks(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserTopTracks("")

            coVerify { spotifyApi.getCurrentUserTopTracks(any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Current User Top Artists")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserTopArtists {

        @Test
        fun `getCurrentUserTopArtists, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserTopArtists.json")
            val expectedResponse = gson.fromJson(json, ArtistList::class.java)

            coEvery { spotifyApi.getCurrentUserTopArtists(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserTopArtists("")

            coVerify { spotifyApi.getCurrentUserTopArtists(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserTopArtists, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.getCurrentUserTopArtists(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserTopArtists("")

            coVerify { spotifyApi.getCurrentUserTopArtists(any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Current User Albums")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserAlbums {

        @Test
        fun `getCurrentUserAlbums, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserAlbums.json")
            val expectedResponse = gson.fromJson(json, SpotifyLibraryAlbumModel::class.java)

            coEvery { spotifyApi.getCurrentUserAlbums(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserAlbums("")

            coVerify { spotifyApi.getCurrentUserAlbums(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserAlbums, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.getCurrentUserAlbums(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserAlbums("")

            coVerify { spotifyApi.getCurrentUserAlbums(any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Current User Shows")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserShows {

        @Test
        fun `getCurrentUserShows, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserShows.json")
            val expectedResponse = gson.fromJson(json, SpotifyLibraryShowsModel::class.java)

            coEvery { spotifyApi.getCurrentUserShows(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserShows("")

            coVerify { spotifyApi.getCurrentUserShows(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserAlbums, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.getCurrentUserShows(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserShows("")

            coVerify { spotifyApi.getCurrentUserShows(any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Current User Episodes")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserEpisodes {

        @Test
        fun `getCurrentUserEpisodes, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserEpisodes.json")
            val expectedResponse = gson.fromJson(json, SpotifyLibraryEpisodesModel::class.java)

            coEvery { spotifyApi.getCurrentUserEpisodes(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserEpisodes("")

            coVerify { spotifyApi.getCurrentUserEpisodes(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserEpisodes, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.getCurrentUserEpisodes(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserEpisodes("")

            coVerify { spotifyApi.getCurrentUserEpisodes(any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Current User Tracks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserTracks {

        @Test
        fun `getCurrentUserTracks, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserTracks.json")
            val expectedResponse = gson.fromJson(json, SpotifyLibraryTracksModel::class.java)

            coEvery { spotifyApi.getCurrentUserTracks(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserTracks("")

            coVerify { spotifyApi.getCurrentUserTracks(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserTracks, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.getCurrentUserTracks(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserTracks("")

            coVerify { spotifyApi.getCurrentUserTracks(any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Current User Playlists")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserPlaylists {

        @Test
        fun `getCurrentUserPlaylists, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserPlaylists.json")
            val expectedResponse = gson.fromJson(json, SpotifyUserPlaylistsModel::class.java)

            coEvery { spotifyApi.getCurrentUserPlaylists(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserPlaylists("")

            coVerify { spotifyApi.getCurrentUserPlaylists(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserPlaylists, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.getCurrentUserPlaylists(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserPlaylists("")

            coVerify { spotifyApi.getCurrentUserPlaylists(any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Current User Recently Played Tracks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserRecentlyPlayedTracks {

        @Test
        fun `getCurrentUserRecentlyPlayedTracks, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserRecentlyPlayerTracks.json")
            val expectedResponse = gson.fromJson(json, SpotifyUserRecentlyPlayedModel::class.java)

            coEvery {
                spotifyApi.getCurrentUserRecentlyPlayedTracks(any(), any())
            } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserRecentlyPlayedTracks("")

            coVerify { spotifyApi.getCurrentUserRecentlyPlayedTracks(any(), any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserRecentlyPlayedTracks, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery {
                spotifyApi.getCurrentUserRecentlyPlayedTracks(
                    any(),
                    any()
                )
            } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserRecentlyPlayedTracks("")

            coVerify { spotifyApi.getCurrentUserRecentlyPlayedTracks(any(), any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get User Playlists")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetUserPlaylists {

        @Test
        fun `getUserPlaylists, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getUserPlaylists.json")
            val expectedResponse = gson.fromJson(json, SpotifyUserPlaylistsModel::class.java)

            coEvery {
                spotifyApi.getUserPlaylists(any(), any())
            } returns expectedResponse

            val response = spotifyRepoImpl.getUserPlaylists("", "")

            coVerify { spotifyApi.getUserPlaylists(any(), any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getUserPlaylists, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.getUserPlaylists(any(), any()) } throws Exception()

            val response = spotifyRepoImpl.getUserPlaylists("", "")

            coVerify { spotifyApi.getUserPlaylists(any(), any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Track Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetTrackDetails {

        @Test
        fun `getTrackDetails, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getTrackDetails.json")
            val expectedResponse = gson.fromJson(json, Track::class.java)

            coEvery {
                spotifyApi.getTrackDetails(any(), any())
            } returns expectedResponse

            val response = spotifyRepoImpl.getTrackDetails("", "")

            coVerify { spotifyApi.getTrackDetails(any(), any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getTrackDetails, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.getTrackDetails(any(), any()) } throws Exception()

            val response = spotifyRepoImpl.getTrackDetails("", "")

            coVerify { spotifyApi.getTrackDetails(any(), any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Album Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAlbumDetails {

        @Test
        fun `getAlbumDetails, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getAlbumDetails.json")
            val expectedResponse = gson.fromJson(json, Album::class.java)

            coEvery {
                spotifyApi.getAlbumDetails(any(), any())
            } returns expectedResponse

            val response = spotifyRepoImpl.getAlbumDetails("", "")

            coVerify { spotifyApi.getAlbumDetails(any(), any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getAlbumDetails, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.getAlbumDetails(any(), any()) } throws Exception()

            val response = spotifyRepoImpl.getAlbumDetails("", "")

            coVerify { spotifyApi.getAlbumDetails(any(), any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Search Query")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class SearchQuery {

        @Test
        fun `searchQuery, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/searchQuery.json")
            val expectedResponse = gson.fromJson(json, SpotifySearchModel::class.java)

            coEvery {
                spotifyApi.searchQuery(any(), any())
            } returns expectedResponse

            val response = spotifyRepoImpl.searchQuery("", "", "", "", "")

            coVerify { spotifyApi.searchQuery(any(), any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `searchQuery, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.searchQuery(any(), any()) } throws Exception()

            val response = spotifyRepoImpl.searchQuery("", "", "", "", "")

            coVerify { spotifyApi.searchQuery(any(), any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Recommendations")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetRecommendations {

        @Test
        fun `getRecommendations, returns Success`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getRecommendations.json")
            val expectedResponse = gson.fromJson(json, SpotifyRecommendationModel::class.java)

            coEvery {
                spotifyApi.getRecommendations(any(), any())
            } returns expectedResponse

            val response = spotifyRepoImpl.getRecommendations("", "", "", "", "")

            coVerify { spotifyApi.getRecommendations(any(), any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getRecommendations, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApi.getRecommendations(any(), any()) } throws Exception()

            val response = spotifyRepoImpl.getRecommendations("", "", "", "", "")

            coVerify { spotifyApi.getRecommendations(any(), any()) }
            assert(response is ResponseState.ErrorMessage)
        }
    }
}