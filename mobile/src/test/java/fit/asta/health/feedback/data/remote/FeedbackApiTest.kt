package fit.asta.health.feedback.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.feedback.data.remote.modal.FeedbackQuesDTO
import fit.asta.health.feedback.data.remote.modal.PostFeedbackDTO
import fit.asta.health.feedback.data.remote.modal.UserFeedbackDTO
import fit.asta.health.feedback.data.repo.FeedbackRepoImpl
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FeedbackApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: FeedbackApi

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(FeedbackApi::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `getFeedbackQuestions, returns Success`() = runTest {
        val dto = FeedbackQuesDTO()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getFeedbackQuestions("", "")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `getFeedbackQuestions, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val repo = FeedbackRepoImpl(api, mockk())
        val data = repo.getFeedbackQuestions("", "")
        server.takeRequest()

        assert(data is ResponseState.Error)
    }

    @Test
    fun `postUserFeedback, returns Success`() = runTest {
        val resDto = PostFeedbackDTO()
        val json = gson.toJson(resDto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)


        val dto = UserFeedbackDTO()
        val data = api.postUserFeedback(dto, emptyList())
        server.takeRequest()

        assertEquals(data, resDto)
    }

    @Test
    fun `postUserFeedback, returns Error`() = runTest {
        val dto = UserFeedbackDTO()
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val repo = FeedbackRepoImpl(api, mockk())
        val data = repo.postUserFeedback(dto)
        server.takeRequest()

        assert(data is ResponseState.Error)
    }
}