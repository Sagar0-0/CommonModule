package fit.asta.health.data.profile.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.BasicProfileResponse
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.data.profile.remote.model.ProfileAvailableStatus
import fit.asta.health.data.profile.repo.ProfileRepoImpl
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

class BasicProfileApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: BasicProfileApi

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(BasicProfileApi::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `checkReferralCode, returns Success`() = runTest {
        val dto = CheckReferralDTO()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.checkReferralCode("")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `checkReferralCode, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val repo = ProfileRepoImpl(api, mockk(), mockk(), UnconfinedTestDispatcher())
        val data = repo.checkReferralCode("")
        server.takeRequest()

        assert(data is ResponseState.Error)
    }

    @Test
    fun `isUserProfileAvailable, returns Success`() = runTest {
        val dto = ProfileAvailableStatus()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.isUserProfileAvailable("")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `createBasicProfile, returns Success`() = runTest {
        val dto = BasicProfileResponse()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.createBasicProfile(BasicProfileDTO(), mockk())
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `createBasicProfile, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val repo = ProfileRepoImpl(api, mockk(), mockk(), UnconfinedTestDispatcher())
        val data = repo.createBasicProfile(BasicProfileDTO())
        server.takeRequest()

        assert(data is ResponseState.Error)
    }
}