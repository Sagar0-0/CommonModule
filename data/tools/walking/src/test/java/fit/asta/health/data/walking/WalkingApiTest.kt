package fit.asta.health.data.walking

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.data.walking.remote.WalkingApi
import fit.asta.health.data.walking.remote.model.HomeData
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WalkingApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: WalkingApi

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WalkingApi::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `getHomeData, returns Success`() = runTest {
        val dto: Response<HomeData> = mockk()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getHomeData("")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `getHomeData, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        try {
            api.getHomeData("")
            server.takeRequest()
        } catch (e: HttpException) {
            assert(e.code() == 404)
        }
    }

    @Test
    fun `getSheetData, returns Success`() = runTest {
        val dto: Response<List<NetSheetData>> = mockk()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getSheetListData("")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `getSheetData, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        try {
            api.getSheetListData("")
            server.takeRequest()
        } catch (e: HttpException) {
            assert(e.code() == 404)
        }
    }

    @Test
    fun `getSheetGoalsData, returns Success`() = runTest {
        val dto: Response<List<NetSheetData>> = mockk()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getSheetGoalsListData("")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `getSheetGoalsData, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        try {
            api.getSheetGoalsListData("")
            server.takeRequest()
        } catch (e: HttpException) {
            assert(e.code() == 404)
        }
    }

    @Test
    fun `putData, returns Success`() = runTest {
        val dto: Response<SubmitProfileResponse> = mockk()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.putData(mockk())
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `putData, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        try {
            api.putData(mockk())
            server.takeRequest()
        } catch (e: HttpException) {
            assert(e.code() == 404)
        }
    }

    @Test
    fun `putDayData, returns Success`() = runTest {
        val dto: Response<SubmitProfileResponse> = mockk()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.putDayData(mockk())
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `putDayData, returns Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        try {
            api.putDayData(mockk())
            server.takeRequest()
        } catch (e: HttpException) {
            assert(e.code() == 404)
        }
    }
}