package fit.asta.health.data.spotify.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.data.spotify.util.JsonReader
import fit.asta.health.data.spotify.model.me.SpotifyMeModel
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
}