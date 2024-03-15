package fit.asta.health.home.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.home.remote.model.ToolsHome
import fit.asta.health.home.repo.ToolsHomeRepoImpl
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ToolsHomeApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: ToolsHomeApi

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ToolsHomeApi::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `getHomeData, returns Success`() = runTest {
        val dto = Response(data = ToolsHome())
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getHomeData("", "", "", "", 0)
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `getHomeData, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val repo = ToolsHomeRepoImpl(api, UnconfinedTestDispatcher())
        val data = repo.getHomeData("", "", "", "", 0, 0, "")
        server.takeRequest()

        assert(data is ResponseState.ErrorMessage)
    }
}