package fit.asta.health.payment.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.payment.remote.model.OrderResponse
import fit.asta.health.payment.remote.model.PaymentResponse
import fit.asta.health.payment.repo.PaymentsRepoImpl
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentsApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: PaymentsApi

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PaymentsApi::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `createOrder, returns Success`() = runTest {
        val dto = Response(data = OrderResponse())
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.createOrder(OrderRequest())
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `createOrder, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val onboardingRepoImpl = PaymentsRepoImpl(api)
        val data = onboardingRepoImpl.createOrder(OrderRequest())
        server.takeRequest()

        assert(data is ResponseState.ErrorMessage)
    }

    @Test
    fun `verifyAndUpdateProfile, returns Success`() = runTest {
        val dto = Response(data = PaymentResponse())
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.verifyAndUpdateProfile("", "")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `verifyAndUpdateProfile, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val onboardingRepoImpl = PaymentsRepoImpl(api)
        val data = onboardingRepoImpl.verifyAndUpdateProfile("", "")
        server.takeRequest()

        assert(data is ResponseState.ErrorMessage)
    }
}