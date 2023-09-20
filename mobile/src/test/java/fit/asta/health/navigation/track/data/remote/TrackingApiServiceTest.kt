package fit.asta.health.navigation.track.data.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.navigation.track.data.remote.model.breathing.BreathingResponse
import fit.asta.health.navigation.track.data.remote.model.exercise.ExerciseResponse
import fit.asta.health.navigation.track.data.remote.model.meditation.MeditationResponse
import fit.asta.health.navigation.track.data.remote.model.menu.HomeMenuResponse
import fit.asta.health.navigation.track.data.remote.model.sleep.SleepResponse
import fit.asta.health.navigation.track.data.remote.model.step.StepsResponse
import fit.asta.health.navigation.track.data.remote.model.sunlight.SunlightResponse
import fit.asta.health.navigation.track.data.remote.model.water.WaterResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TrackingApiServiceTest {

    // Mock Server
    private lateinit var server: MockWebServer
    // Service Api
    private lateinit var api: TrackingApiService
    // GSON object
    private val gson: Gson = GsonBuilder().create()

    @BeforeEach
    fun beforeEach() {

        // Defining all the static functions return
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0

        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(TrackingApiService::class.java)
    }

    @AfterEach
    fun afterEach() {
        server.shutdown()
    }


    @Nested
    @DisplayName("Get Home Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetHomeDetails {

        @Test
        fun `getHomeDetails, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            val dto = mockk<HomeMenuResponse>()
            val json = gson.toJson(dto)

            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getHomeDetails("", "", "")
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, dto)
        }

        @Test
        fun `getHomeDetails, returns Error`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<HomeMenuResponse> = getResponseState {
                api.getHomeDetails("", "", "")
            }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Water Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetWaterDetails {

        @Test
        fun `getWaterDetails, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            val dto = mockk<WaterResponse>()
            val json = gson.toJson(dto)

            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getWaterDetails("", "", "", "")
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, dto)
        }

        @Test
        fun `getWaterDetails, returns Error`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<WaterResponse> = getResponseState {
                api.getWaterDetails("", "", "", "")
            }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Steps Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetStepsDetails {

        @Test
        fun `getStepsDetails, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            val dto = mockk<StepsResponse>()
            val json = gson.toJson(dto)

            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getStepsDetails("", "", "", "")
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, dto)
        }

        @Test
        fun `getStepsDetails, returns Error`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<StepsResponse> = getResponseState {
                api.getStepsDetails("", "", "", "")
            }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Meditation Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetMeditationDetails {

        @Test
        fun `getMeditationDetails, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            val dto = mockk<MeditationResponse>()
            val json = gson.toJson(dto)

            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getMeditationDetails("", "", "", "")
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, dto)
        }

        @Test
        fun `getMeditationDetails, returns Error`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<MeditationResponse> = getResponseState {
                api.getMeditationDetails("", "", "", "")
            }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Breathing Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBreathingDetails {

        @Test
        fun `getBreathingDetails, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            val dto = mockk<BreathingResponse>()
            val json = gson.toJson(dto)

            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getBreathingDetails("", "", "", "")
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, dto)
        }

        @Test
        fun `getBreathingDetails, returns Error`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<BreathingResponse> = getResponseState {
                api.getBreathingDetails("", "", "", "")
            }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Sleep Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetSleepDetails {

        @Test
        fun `getSleepDetails, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            val dto = mockk<SleepResponse>()
            val json = gson.toJson(dto)

            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getSleepDetails("", "", "", "")
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, dto)
        }

        @Test
        fun `getSleepDetails, returns Error`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<SleepResponse> = getResponseState {
                api.getSleepDetails("", "", "", "")
            }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Sunlight Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetSunlightDetails {

        @Test
        fun `getSunlightDetails, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            val dto = mockk<SunlightResponse>()
            val json = gson.toJson(dto)

            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getSunlightDetails("", "", "", "")
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, dto)
        }

        @Test
        fun `getSunlightDetails, returns Error`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<SunlightResponse> = getResponseState {
                api.getSunlightDetails("", "", "", "")
            }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Exercise Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetExerciseDetails {

        @Test
        fun `getExerciseDetails, returns Success`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()

            val dto = mockk<ExerciseResponse>()
            val json = gson.toJson(dto)

            mockResponse.setBody(json)
            server.enqueue(mockResponse)

            // Response from the Server
            val response = api.getExerciseDetails("", "", "", "", "")
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assertEquals(response, dto)
        }

        @Test
        fun `getExerciseDetails, returns Error`() = runTest {

            // Mock Response
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(404)
            server.enqueue(mockResponse)

            // Response from the Server
            val response: ResponseState<ExerciseResponse> = getResponseState {
                api.getExerciseDetails("", "", "", "", "")
            }
            server.takeRequest()

            // Assertion to check if the response is correct or not
            assert(response is ResponseState.ErrorMessage)
        }
    }

}