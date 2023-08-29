package fit.asta.health.data.spotify.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.data.spotify.model.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.data.spotify.model.library.following.SpotifyUserFollowingArtist
import fit.asta.health.data.spotify.model.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.data.spotify.util.JsonReader
import fit.asta.health.data.spotify.model.me.SpotifyMeModel
import fit.asta.health.data.spotify.model.search.ArtistList
import fit.asta.health.data.spotify.model.search.TrackList
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals

class SpotifyApiServiceTest {

    // Mock Server
    private lateinit var server: MockWebServer

    // Spotify Service Api
    private lateinit var api: SpotifyApiService

    // GSON object
    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SpotifyApiService::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }


    @Nested
    @DisplayName("Get Current User Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUser {

        @Test
        fun `getCurrentUserDetails, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/currentUserDetails.json")
            val localJsonData = gson.fromJson(json, SpotifyMeModel::class.java)
            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getCurrentUserDetails(mapOf())
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, localJsonData)
        }


        @Test
        fun `getCurrentUserDetails, returns Failed`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<SpotifyMeModel> =
                getResponseState { api.getCurrentUserDetails(mapOf()) }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.Error)
        }
    }


    @Nested
    @DisplayName("Get Current User Followed Artists")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserFollowedArtists {

        @Test
        fun `getCurrentUserFollowedArtists, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserFollowedArtists.json")
            val localJsonData = gson.fromJson(json, SpotifyUserFollowingArtist::class.java)
            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getCurrentUserFollowedArtists(mapOf())
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, localJsonData)
        }

        @Test
        fun `getCurrentUserFollowedArtists, returns Failed`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<SpotifyUserFollowingArtist> =
                getResponseState { api.getCurrentUserFollowedArtists(mapOf()) }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.Error)
        }
    }


    @Nested
    @DisplayName("Get Current User Top Tracks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserTopTracks {

        @Test
        fun `getCurrentUserTopTracks, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserTopTracks.json")
            val localJsonData = gson.fromJson(json, TrackList::class.java)
            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getCurrentUserTopTracks(mapOf())
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, localJsonData)
        }

        @Test
        fun `getCurrentUserTopTracks, returns Failed`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<TrackList> =
                getResponseState { api.getCurrentUserTopTracks(mapOf()) }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.Error)
        }
    }


    @Nested
    @DisplayName("Get Current User Top Artists")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserTopArtists {

        @Test
        fun `getCurrentUserTopArtists, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserTopArtists.json")
            val localJsonData = gson.fromJson(json, ArtistList::class.java)
            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getCurrentUserTopArtists(mapOf())
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, localJsonData)
        }

        @Test
        fun `getCurrentUserTopArtists, returns Failed`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<ArtistList> =
                getResponseState { api.getCurrentUserTopArtists(mapOf()) }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.Error)
        }
    }


    @Nested
    @DisplayName("Get Current User Playlists")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserPlaylists {

        @Test
        fun `getCurrentUserPlaylists, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserPlaylists.json")
            val localJsonData = gson.fromJson(json, SpotifyUserPlaylistsModel::class.java)
            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getCurrentUserPlaylists(mapOf())
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, localJsonData)
        }

        @Test
        fun `getCurrentUserPlaylists, returns Failed`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<SpotifyUserPlaylistsModel> =
                getResponseState { api.getCurrentUserPlaylists(mapOf()) }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.Error)
        }
    }


    @Nested
    @DisplayName("Get Current User Albums")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetCurrentUserAlbums {

        @Test
        fun `getCurrentUserAlbums, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            // Reading JSON File from the resources folder
            val json = JsonReader.readJsonFile("/json/getCurrentUserAlbums.json")
            val localJsonData = gson.fromJson(json, SpotifyLibraryAlbumModel::class.java)
            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getCurrentUserAlbums(mapOf())
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, localJsonData)
        }

        @Test
        fun `getCurrentUserAlbums, returns Failed`() = runTest {

            // Defining all the static functions return
            mockkStatic(Log::class)
            every { Log.e(any(), any()) } returns 0

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<SpotifyLibraryAlbumModel> =
                getResponseState { api.getCurrentUserAlbums(mapOf()) }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.Error)
        }
    }
}