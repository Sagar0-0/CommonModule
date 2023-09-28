package fit.asta.health.data.orders.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.orders.remote.model.OrderData
import fit.asta.health.data.orders.repo.OrdersRepoImpl
import io.mockk.mockk
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

class OrdersApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: OrdersApi

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(OrdersApi::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `getData, returns Success`() = runTest {
        val dto: Response<List<OrderData>> = mockk()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getOrders("")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `getData, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val onboardingRepoImpl = OrdersRepoImpl(api, UnconfinedTestDispatcher())
        val data = onboardingRepoImpl.getOrders("")
        server.takeRequest()

        assert(data is ResponseState.ErrorMessage)
    }
}