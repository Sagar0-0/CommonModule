package fit.asta.health.data.onboarding.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.onboarding.model.OnboardingData
import fit.asta.health.data.onboarding.repo.OnboardingRepoImpl
import fit.asta.health.datastore.PrefManager
import fit.asta.health.datastore.UserPreferencesData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OnboardingApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: OnboardingApi

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(OnboardingApi::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `getData, returns Success`() = runTest {
        val dto: Response<List<OnboardingData>> = mockk()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getData()
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `getData, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val pref: PrefManager = mockk()
        every { pref.userData } returns MutableStateFlow(UserPreferencesData())
        val onboardingRepoImpl = OnboardingRepoImpl(api, pref)
        val data = onboardingRepoImpl.getData()
        server.takeRequest()

        assert(data is ResponseState.ErrorMessage)
    }
}