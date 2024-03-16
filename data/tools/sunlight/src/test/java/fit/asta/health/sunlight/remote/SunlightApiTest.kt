package fit.asta.health.sunlight.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.Response
import fit.asta.health.sunlight.remote.model.HelpAndNutrition
import fit.asta.health.sunlight.remote.model.SessionDetailBody
import fit.asta.health.sunlight.remote.model.SkinConditionBody
import fit.asta.health.sunlight.remote.model.SkinConditionResponseData
import fit.asta.health.sunlight.remote.model.SunlightHomeData
import fit.asta.health.sunlight.remote.model.SunlightSessionData
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SunlightApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: SunlightApi

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SunlightApi::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `getSunlightHomeScreen, returns Success`() = runTest {
        val dto = SunlightHomeData()
        val json = gson.toJson(Response(data = dto))
        val res = MockResponse()
        res.setResponseCode(200)
        res.setBody(json)
        server.enqueue(res)
        val data = api.getSunlightHomeScreen("","","",0,"")
        server.takeRequest()
        Assertions.assertEquals(data.data, dto)
    }

    @Test
    fun `getSunlightHomeScreen, returns error`() = runTest {
        val dto = SunlightHomeData()
        val json = gson.toJson(Response(data = null, status =Response.Status(code = 2) ))
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)
        val data = api.getSunlightHomeScreen("","","",0,"")
        server.takeRequest()
        Assertions.assertEquals(data.data, null)
    }

   @Test
    fun `getScreenContentList, returns Success`() = runTest {
        val dto = listOf (SkinConditionResponseData())
        val json = gson.toJson(Response(data = dto))
        val res = MockResponse()
        res.setResponseCode(200)
        res.setBody(json)
        server.enqueue(res)
        val data = api.getScreenContentList("")
        server.takeRequest()
        Assertions.assertEquals(data.data, dto)
    }

    @Test
    fun `getScreenContentList, returns error`() = runTest {
        val json = gson.toJson(Response(data = null, status =Response.Status(code = 2) ))
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)
        val data = api.getScreenContentList("")
        server.takeRequest()
        Assertions.assertEquals(data.data, null)
    }

   @Test
    fun `updateSkinConditionData, returns Success`() = runTest {
        val dto = PutResponse()
        val json = gson.toJson(Response(data = dto))
        val res = MockResponse()
        res.setResponseCode(200)
        res.setBody(json)
        server.enqueue(res)
        val data = api.updateSkinConditionData(SkinConditionBody())
        server.takeRequest()
        Assertions.assertEquals(data.data, dto)
    }

    @Test
    fun `updateSkinConditionData, returns error`() = runTest {
        val json = gson.toJson(Response(data = null, status =Response.Status(code = 2) ))
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)
        val data = api.updateSkinConditionData(SkinConditionBody())
        server.takeRequest()
        Assertions.assertEquals(data.data, null)
    }


    @Test
    fun `getScreenContentList, returns Error`() = runTest {
        val res = MockResponse().setResponseCode(404)
        server.enqueue(res)
        val data = api.getScreenContentList("")
        server.takeRequest()
        Assertions.assertNull(data.data)
    }


    @Test
    fun `updateSkinConditionData, returns Error`() = runTest {
        val res = MockResponse().setResponseCode(500)
        server.enqueue(res)
        val data = api.updateSkinConditionData(SkinConditionBody())
        server.takeRequest()
        Assertions.assertNull(data.data)
    }

    @Test
    fun `getSupplementAndFoodInfo, returns Success`() = runTest {
        val dto = HelpAndNutrition()
        val json = gson.toJson(Response(data = dto))
        val res = MockResponse()
        res.setResponseCode(200)
        res.setBody(json)
        server.enqueue(res)
        val data = api.getSupplementAndFoodInfo()
        server.takeRequest()
        Assertions.assertEquals(data.data, dto)
    }

    @Test
    fun `getSupplementAndFoodInfo, returns Error`() = runTest {
        val res = MockResponse().setResponseCode(403)
        server.enqueue(res)
        val data = api.getSupplementAndFoodInfo()
        server.takeRequest()
        Assertions.assertNull(data.data)
    }

    @Test
    fun `getSessionDetail, returns Success`() = runTest {
        val dto = SunlightSessionData()
        val json = gson.toJson(Response(data = dto))
        val res = MockResponse()
        res.setResponseCode(200)
        res.setBody(json)
        server.enqueue(res)
        val data = api.getSessionDetail(SessionDetailBody())
        server.takeRequest()
        Assertions.assertEquals(data.data, dto)
    }

    @Test
    fun `getSessionDetail, returns Error`() = runTest {
        val res = MockResponse().setResponseCode(401)
        server.enqueue(res)
        val data = api.getSessionDetail(SessionDetailBody())
        server.takeRequest()
        Assertions.assertNull(data.data)
    }





}