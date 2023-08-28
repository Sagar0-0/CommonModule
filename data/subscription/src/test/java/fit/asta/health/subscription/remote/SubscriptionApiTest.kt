package fit.asta.health.subscription.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.subscription.remote.model.SubscriptionResponse
import fit.asta.health.subscription.repo.SubscriptionRepoImpl
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

class SubscriptionApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: SubscriptionApi

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SubscriptionApi::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `getData, returns Success`() = runTest {
        val dto = SubscriptionResponse()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getData("", "")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `getData, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val repo = SubscriptionRepoImpl(api, UnconfinedTestDispatcher())
        val data = repo.getData("", "")
        server.takeRequest()

        assert(data is ResponseState.Error)
    }
}