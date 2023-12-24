package fit.asta.health.data.testimonials.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.testimonials.model.SaveTestimonialResponse
import fit.asta.health.data.testimonials.model.Testimonial
import fit.asta.health.data.testimonials.repo.TestimonialRepoImpl
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

class TestimonialApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: TestimonialApi

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TestimonialApi::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `getTestimonials, returns Success`() = runTest {
        val dto: Response<List<Testimonial>> = mockk()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getAllTestimonials(0, 0)
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `getTestimonials, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val testimonialRepoImpl = TestimonialRepoImpl(mockk(), api, UnconfinedTestDispatcher())
        val data = testimonialRepoImpl.getAllTestimonials(0, 0)
        server.takeRequest()

        assert(data is ResponseState.ErrorMessage)
    }

    @Test
    fun `createTestimonial, returns Success`() = runTest {
        val dto: Response<SaveTestimonialResponse> = Response(data = SaveTestimonialResponse())
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.createTestimonial(Testimonial(), listOf())
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `createTestimonial, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val testimonialRepoImpl = TestimonialRepoImpl(mockk(), api, UnconfinedTestDispatcher())
        val data = testimonialRepoImpl.saveUserTestimonial(Testimonial())
        server.takeRequest()

        assert(data is ResponseState.ErrorMessage)
    }

    @Test
    fun `getUserTestimonial, returns Success`() = runTest {
        val dto: Response<Testimonial> = Response(data = Testimonial())
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getUserTestimonial("")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `getUserTestimonial, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val testimonialRepoImpl = TestimonialRepoImpl(mockk(), api, UnconfinedTestDispatcher())
        val data = testimonialRepoImpl.getUserTestimonial("")
        server.takeRequest()

        assert(data is ResponseState.ErrorMessage)
    }
}