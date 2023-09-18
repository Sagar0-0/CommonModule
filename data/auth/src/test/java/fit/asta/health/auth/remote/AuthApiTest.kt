package fit.asta.health.auth.remote

import app.cash.turbine.test
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.auth.repo.AuthRepoImpl
import fit.asta.health.common.utils.ResponseState
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

class AuthApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: AuthApi

    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(AuthApi::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }

    @Test
    fun `deleteAccount, return Success`() = runTest {
        val dto = DeleteAccountResponse()
        val json = gson.toJson(dto)!!
        val res = MockResponse()
        res.setBody(json)
        server.enqueue(res)

        val data = api.deleteAccount("")
        server.takeRequest()

        assertEquals(data, dto)
    }

    @Test
    fun `getAddresses, return Error`() = runTest {
        val res = MockResponse()
        res.setResponseCode(404)
        server.enqueue(res)

        val pref: PrefManager = mockk()
        val firebaseAuth: FirebaseAuth = mockk()
        every { firebaseAuth.currentUser } returns mockk()
        every { pref.userData } returns MutableStateFlow(UserPreferencesData())
        val repo = AuthRepoImpl(mockk(), api, mockk(), firebaseAuth, pref)
        val data = repo.deleteAccount()
        server.takeRequest()
        data.test {
            assert(awaitItem() is ResponseState.Error)
        }
    }
}