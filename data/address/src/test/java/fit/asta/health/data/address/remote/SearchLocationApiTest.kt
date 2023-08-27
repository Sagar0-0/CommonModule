package fit.asta.health.data.address.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.address.modal.SearchResponse
import fit.asta.health.data.address.repo.AddressRepoImpl
import fit.asta.health.datastore.PrefManager
import fit.asta.health.datastore.UserPreferencesData
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
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

class SearchLocationApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: SearchLocationApi

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SearchLocationApi::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `search,return Success`() = runTest {
        val dto = SearchResponse()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.search("", "")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `searchBiased,return Success`() = runTest {
        val dto = SearchResponse()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.searchBiased("", "", "", "")
        server.takeRequest()

        assertEquals(data, dto)
    }


    @Test
    fun `search, return Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val pref: PrefManager = mockk()
        coEvery { pref.userData } returns MutableStateFlow(UserPreferencesData())
        val repo = AddressRepoImpl(
            mockk(), api, pref, mockk(), mockk(),
            UnconfinedTestDispatcher()
        )
        val data = repo.search("", 0.00, 0.00)
        server.takeRequest()

        assert(data is ResponseState.Error)
    }

    @Test
    fun `searchBiased, return Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val pref: PrefManager = mockk()
        every { pref.userData } returns MutableStateFlow(UserPreferencesData())
        val repo = AddressRepoImpl(mockk(), api, pref, mockk(), mockk(), UnconfinedTestDispatcher())
        val data = repo.search("", 1.00, 1.00)
        server.takeRequest()

        assert(data is ResponseState.Error)
    }
}