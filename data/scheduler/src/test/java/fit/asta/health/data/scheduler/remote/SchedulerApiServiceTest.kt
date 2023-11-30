package fit.asta.health.data.scheduler.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.Response
import fit.asta.health.data.scheduler.remote.model.TodaySchedules
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerDeleteResponse
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerGetListResponse
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerGetResponse
import fit.asta.health.data.scheduler.remote.net.scheduler.AstaSchedulerPutResponse
import fit.asta.health.data.scheduler.remote.net.scheduler.Status
import fit.asta.health.data.scheduler.remote.net.tag.NetCustomTag
import fit.asta.health.data.scheduler.remote.net.tag.TagsListResponse
import fit.asta.health.network.data.ServerRes
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MultipartBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SchedulerApiServiceTest {
    private lateinit var server: MockWebServer
    private lateinit var api: SchedulerApiService

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SchedulerApiService::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `getTodayDataFromBackend,return Success`() = runTest {
        val dto = TodaySchedules()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getTodayDataFromBackend("", "", "", 0f, 0f)
        server.takeRequest()

        assertEquals(data.data, dto)
    }

    @Test
    fun `getTodayDataFromBackend,return Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)
        try {
            api.getTodayDataFromBackend("", "", "", 0f, 0f)
            server.takeRequest()
        } catch (e: HttpException) {
            assert(e.code() == 404)
        }
    }

    @Test
    fun `updateScheduleDataOnBackend,return Success`() = runTest {
        val dto = AstaSchedulerPutResponse()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.updateScheduleDataOnBackend(mockk())
        server.takeRequest()

        assertEquals(data.data.flag, dto.data.flag)
    }

    @Test
    fun `getScheduleDataFromBackend,return Success`() = runTest {
        val dto = AstaSchedulerGetResponse(
            data = mockk(), status = Status(200, "")
        )
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getScheduleDataFromBackend("")
        server.takeRequest()

        assertEquals(data.status.code, dto.status.code)
    }

    @Test
    fun `getScheduleListDataFromBackend,return Success`() = runTest {
        val dto = AstaSchedulerGetListResponse(
            data = listOf(), status = Status(200, "")
        )
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getScheduleListDataFromBackend("")
        server.takeRequest()

        assertEquals(data.status.code, dto.status.code)
    }

    @Test
    fun `deleteScheduleDataFromBackend,return Success`() = runTest {
        val dto = AstaSchedulerDeleteResponse(
            data = mockk(), status = Status(200, "")
        )
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.deleteScheduleDataFromBackend("")
        server.takeRequest()

        assertEquals(data.status.code, dto.status.code)
    }

    @Test
    fun `getTagListFromBackend,return Success`() = runTest {
        val dto: Response<TagsListResponse> = mockk()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getTagListFromBackend("")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `deleteTagFromBackend,return Success`() = runTest {
        val dto: Response<ServerRes> = mockk()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.deleteTagFromBackend("", "")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `updateScheduleTag,return Success`() = runTest {
        val dto: Response<ServerRes> = mockk()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)
        val file = MultipartBody.Builder()
            .addFormDataPart("file", "file.txt")
            .build()
        val data = api.updateScheduleTag(NetCustomTag(), file.part(0))
        val requestReceived = server.takeRequest()
        assertEquals(requestReceived.method, "PUT")
        assertEquals(requestReceived.path, "/tag/put")
        assertEquals(data, dto)
    }
}