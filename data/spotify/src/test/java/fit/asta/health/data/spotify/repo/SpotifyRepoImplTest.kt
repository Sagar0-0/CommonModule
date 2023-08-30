package fit.asta.health.data.spotify.repo

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.spotify.model.common.Album
import fit.asta.health.data.spotify.model.common.Track
import fit.asta.health.data.spotify.model.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.data.spotify.model.library.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.data.spotify.model.library.following.SpotifyUserFollowingArtist
import fit.asta.health.data.spotify.model.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.data.spotify.model.library.shows.SpotifyLibraryShowsModel
import fit.asta.health.data.spotify.model.library.tracks.SpotifyLibraryTracksModel
import fit.asta.health.data.spotify.model.me.SpotifyMeModel
import fit.asta.health.data.spotify.model.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.data.spotify.model.recommendations.SpotifyRecommendationModel
import fit.asta.health.data.spotify.model.search.ArtistList
import fit.asta.health.data.spotify.model.search.SpotifySearchModel
import fit.asta.health.data.spotify.model.search.TrackList
import fit.asta.health.data.spotify.remote.SpotifyApiService
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
    lateinit var spotifyApiService: SpotifyApiService
    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        spotifyRepoImpl = spyk(SpotifyRepoImpl(spotifyApiService))
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

            coEvery { spotifyApiService.getCurrentUserDetails(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserDetails("")

            coVerify { spotifyApiService.getCurrentUserDetails(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserDetails, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.getCurrentUserDetails(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserDetails("")

            coVerify { spotifyApiService.getCurrentUserDetails(any()) }
            assert(response is ResponseState.Error)
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

            coEvery { spotifyApiService.getCurrentUserFollowedArtists(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserFollowedArtists("")

            coVerify { spotifyApiService.getCurrentUserFollowedArtists(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserFollowedArtists, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.getCurrentUserFollowedArtists(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserFollowedArtists("")

            coVerify { spotifyApiService.getCurrentUserFollowedArtists(any()) }
            assert(response is ResponseState.Error)
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

            coEvery { spotifyApiService.getCurrentUserTopTracks(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserTopTracks("")

            coVerify { spotifyApiService.getCurrentUserTopTracks(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserTopTracks, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.getCurrentUserTopTracks(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserTopTracks("")

            coVerify { spotifyApiService.getCurrentUserTopTracks(any()) }
            assert(response is ResponseState.Error)
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

            coEvery { spotifyApiService.getCurrentUserTopArtists(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserTopArtists("")

            coVerify { spotifyApiService.getCurrentUserTopArtists(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserTopArtists, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.getCurrentUserTopArtists(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserTopArtists("")

            coVerify { spotifyApiService.getCurrentUserTopArtists(any()) }
            assert(response is ResponseState.Error)
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

            coEvery { spotifyApiService.getCurrentUserAlbums(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserAlbums("")

            coVerify { spotifyApiService.getCurrentUserAlbums(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserAlbums, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.getCurrentUserAlbums(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserAlbums("")

            coVerify { spotifyApiService.getCurrentUserAlbums(any()) }
            assert(response is ResponseState.Error)
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

            coEvery { spotifyApiService.getCurrentUserShows(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserShows("")

            coVerify { spotifyApiService.getCurrentUserShows(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserAlbums, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.getCurrentUserShows(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserShows("")

            coVerify { spotifyApiService.getCurrentUserShows(any()) }
            assert(response is ResponseState.Error)
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

            coEvery { spotifyApiService.getCurrentUserEpisodes(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserEpisodes("")

            coVerify { spotifyApiService.getCurrentUserEpisodes(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserEpisodes, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.getCurrentUserEpisodes(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserEpisodes("")

            coVerify { spotifyApiService.getCurrentUserEpisodes(any()) }
            assert(response is ResponseState.Error)
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

            coEvery { spotifyApiService.getCurrentUserTracks(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserTracks("")

            coVerify { spotifyApiService.getCurrentUserTracks(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserTracks, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.getCurrentUserTracks(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserTracks("")

            coVerify { spotifyApiService.getCurrentUserTracks(any()) }
            assert(response is ResponseState.Error)
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

            coEvery { spotifyApiService.getCurrentUserPlaylists(any()) } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserPlaylists("")

            coVerify { spotifyApiService.getCurrentUserPlaylists(any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserPlaylists, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.getCurrentUserPlaylists(any()) } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserPlaylists("")

            coVerify { spotifyApiService.getCurrentUserPlaylists(any()) }
            assert(response is ResponseState.Error)
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
                spotifyApiService.getCurrentUserRecentlyPlayedTracks(any(), any())
            } returns expectedResponse

            val response = spotifyRepoImpl.getCurrentUserRecentlyPlayedTracks("")

            coVerify { spotifyApiService.getCurrentUserRecentlyPlayedTracks(any(), any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getCurrentUserRecentlyPlayedTracks, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery {
                spotifyApiService.getCurrentUserRecentlyPlayedTracks(
                    any(),
                    any()
                )
            } throws Exception()

            val response = spotifyRepoImpl.getCurrentUserRecentlyPlayedTracks("")

            coVerify { spotifyApiService.getCurrentUserRecentlyPlayedTracks(any(), any()) }
            assert(response is ResponseState.Error)
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
                spotifyApiService.getUserPlaylists(any(), any())
            } returns expectedResponse

            val response = spotifyRepoImpl.getUserPlaylists("", "")

            coVerify { spotifyApiService.getUserPlaylists(any(), any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getUserPlaylists, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.getUserPlaylists(any(), any()) } throws Exception()

            val response = spotifyRepoImpl.getUserPlaylists("", "")

            coVerify { spotifyApiService.getUserPlaylists(any(), any()) }
            assert(response is ResponseState.Error)
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
                spotifyApiService.getTrackDetails(any(), any())
            } returns expectedResponse

            val response = spotifyRepoImpl.getTrackDetails("", "")

            coVerify { spotifyApiService.getTrackDetails(any(), any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getTrackDetails, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.getTrackDetails(any(), any()) } throws Exception()

            val response = spotifyRepoImpl.getTrackDetails("", "")

            coVerify { spotifyApiService.getTrackDetails(any(), any()) }
            assert(response is ResponseState.Error)
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
                spotifyApiService.getAlbumDetails(any(), any())
            } returns expectedResponse

            val response = spotifyRepoImpl.getAlbumDetails("", "")

            coVerify { spotifyApiService.getAlbumDetails(any(), any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getAlbumDetails, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.getAlbumDetails(any(), any()) } throws Exception()

            val response = spotifyRepoImpl.getAlbumDetails("", "")

            coVerify { spotifyApiService.getAlbumDetails(any(), any()) }
            assert(response is ResponseState.Error)
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
                spotifyApiService.searchQuery(any(), any())
            } returns expectedResponse

            val response = spotifyRepoImpl.searchQuery("", "", "", "", "")

            coVerify { spotifyApiService.searchQuery(any(), any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `searchQuery, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.searchQuery(any(), any()) } throws Exception()

            val response = spotifyRepoImpl.searchQuery("", "", "", "", "")

            coVerify { spotifyApiService.searchQuery(any(), any()) }
            assert(response is ResponseState.Error)
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
                spotifyApiService.getRecommendations(any(), any())
            } returns expectedResponse

            val response = spotifyRepoImpl.getRecommendations("", "", "", "", "")

            coVerify { spotifyApiService.getRecommendations(any(), any()) }
            assert(response is ResponseState.Success)
        }

        @Test
        fun `getRecommendations, returns Error`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            coEvery { spotifyApiService.getRecommendations(any(), any()) } throws Exception()

            val response = spotifyRepoImpl.getRecommendations("", "", "", "", "")

            coVerify { spotifyApiService.getRecommendations(any(), any()) }
            assert(response is ResponseState.Error)
        }
    }
}