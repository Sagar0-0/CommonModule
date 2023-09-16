package fit.asta.health.data.address.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.address.remote.modal.AddressesDTO
import fit.asta.health.data.address.remote.modal.DeleteAddressResponse
import fit.asta.health.data.address.remote.modal.PutAddressResponse
import fit.asta.health.data.address.repo.AddressRepoImpl
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

class AddressApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: AddressApi

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(AddressApi::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `getAddresses, return Success`() = runTest {
        val dto = AddressesDTO()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.getAddresses("")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `getAddresses, return Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val pref: PrefManager = mockk()
        every { pref.userData } returns MutableStateFlow(UserPreferencesData())
        val repo = AddressRepoImpl(api, mockk(), pref, mockk(), mockk())
        val data = repo.getSavedAddresses("")
        server.takeRequest()

        assert(data is ResponseState.Error)
    }

    @Test
    fun `putAddress, return Success`() = runTest {
        val dto = PutAddressResponse()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.putAddress(mockk())
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `putAddress, return Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val pref: PrefManager = mockk()
        every { pref.userData } returns MutableStateFlow(UserPreferencesData())
        val repo = AddressRepoImpl(api, mockk(), pref, mockk(), mockk())
        val data = repo.putAddress(mockk())
        server.takeRequest()

        assert(data is ResponseState.Error)
    }

    @Test
    fun `deleteAddress, return Success`() = runTest {
        val dto = DeleteAddressResponse()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.deleteAddress("", "")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `deleteAddress, return Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val pref: PrefManager = mockk()
        every { pref.userData } returns MutableStateFlow(UserPreferencesData())
        val repo = AddressRepoImpl(api, mockk(), pref, mockk(), mockk())
        val data = repo.deleteAddress("", "")
        server.takeRequest()

        assert(data is ResponseState.Error)
    }

    @Test
    fun `selectCurrent, return Success`() = runTest {
        val res = MockResponse()
        res.setResponseCode(200)
        server.enqueue(res)

        val pref: PrefManager = mockk()
        every { pref.userData } returns MutableStateFlow(UserPreferencesData())
        val repo = AddressRepoImpl(api, mockk(), pref, mockk(), mockk())
        val data = repo.selectAddress("", "")
        server.takeRequest()

        assert(data is ResponseState.Success)
    }

    @Test
    fun `selectCurrent, return Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val pref: PrefManager = mockk()
        every { pref.userData } returns MutableStateFlow(UserPreferencesData())
        val repo = AddressRepoImpl(api, mockk(), pref, mockk(), mockk())
        val data = repo.selectAddress("", "")
        server.takeRequest()

        assert(data is ResponseState.Error)
    }
}