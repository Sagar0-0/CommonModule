package fit.asta.health.navigation.track.data.repo

import android.util.Log
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.navigation.track.data.remote.TrackingApiService
import fit.asta.health.navigation.track.data.remote.model.breathing.BreathingResponse
import fit.asta.health.navigation.track.data.remote.model.exercise.ExerciseResponse
import fit.asta.health.navigation.track.data.remote.model.meditation.MeditationResponse
import fit.asta.health.navigation.track.data.remote.model.menu.HomeMenuResponse
import fit.asta.health.navigation.track.data.remote.model.sleep.SleepResponse
import fit.asta.health.navigation.track.data.remote.model.step.StepsResponse
import fit.asta.health.navigation.track.data.remote.model.sunlight.SunlightResponse
import fit.asta.health.navigation.track.data.remote.model.water.WaterResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

class TrackingRepoImplText {

    private lateinit var repository: TrackingRepoImpl

    @RelaxedMockK
    lateinit var trackingApiService: TrackingApiService

    @BeforeEach
    fun beforeEach() {

        // Defining all the static functions return
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0

        MockKAnnotations.init(this, relaxed = true)
        repository = spyk(TrackingRepoImpl(trackingApiService))
    }


    @Nested
    @DisplayName("Get Home Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetHomeDetails {

        @Test
        fun `getHomeDetails, returns Success`() = runTest {

            val expected = mockk<HomeMenuResponse>()

            coEvery {
                trackingApiService.getHomeDetails("", "", "")
            } returns expected

            val response = repository.getHomeDetails("", "", "")

            coVerify { trackingApiService.getHomeDetails("", "", "") }
            assert(response is ResponseState.Success)
            assertEquals((response as ResponseState.Success).data, expected)
        }

        @Test
        fun `getHomeDetails, returns Error`() = runTest {

            coEvery {
                trackingApiService.getHomeDetails("", "", "")
            } throws Exception()

            val response = repository.getHomeDetails("", "", "")

            coVerify { trackingApiService.getHomeDetails("", "", "") }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Water Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetWaterDetails {

        @Test
        fun `getWaterDetails, returns Success`() = runTest {

            val expected = mockk<WaterResponse>()

            coEvery {
                trackingApiService.getWaterDetails("", "", "", "")
            } returns expected

            val response = repository.getWaterDetails("", "", "", "")

            coVerify { trackingApiService.getWaterDetails("", "", "", "") }
            assert(response is ResponseState.Success)
            assertEquals((response as ResponseState.Success).data, expected)
        }

        @Test
        fun `getWaterDetails, returns Error`() = runTest {

            coEvery {
                trackingApiService.getWaterDetails("", "", "", "")
            } throws Exception()

            val response = repository.getWaterDetails("", "", "", "")

            coVerify { trackingApiService.getWaterDetails("", "", "", "") }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Steps Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetStepsDetails {

        @Test
        fun `getStepsDetails, returns Success`() = runTest {

            val expected = mockk<StepsResponse>()

            coEvery {
                trackingApiService.getStepsDetails("", "", "", "")
            } returns expected

            val response = repository.getStepsDetails("", "", "", "")

            coVerify { trackingApiService.getStepsDetails("", "", "", "") }
            assert(response is ResponseState.Success)
            assertEquals((response as ResponseState.Success).data, expected)
        }

        @Test
        fun `getStepsDetails, returns Error`() = runTest {

            coEvery {
                trackingApiService.getStepsDetails("", "", "", "")
            } throws Exception()

            val response = repository.getStepsDetails("", "", "", "")

            coVerify { trackingApiService.getStepsDetails("", "", "", "") }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Meditation Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetMeditationDetails {

        @Test
        fun `getMeditationDetails, returns Success`() = runTest {

            val expected = mockk<MeditationResponse>()

            coEvery {
                trackingApiService.getMeditationDetails("", "", "", "")
            } returns expected

            val response = repository.getMeditationDetails("", "", "", "")

            coVerify { trackingApiService.getMeditationDetails("", "", "", "") }
            assert(response is ResponseState.Success)
            assertEquals((response as ResponseState.Success).data, expected)
        }

        @Test
        fun `getMeditationDetails, returns Error`() = runTest {

            coEvery {
                trackingApiService.getMeditationDetails("", "", "", "")
            } throws Exception()

            val response = repository.getMeditationDetails("", "", "", "")

            coVerify { trackingApiService.getMeditationDetails("", "", "", "") }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Breathing Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBreathingDetails {

        @Test
        fun `getBreathingDetails, returns Success`() = runTest {

            val expected = mockk<BreathingResponse>()

            coEvery {
                trackingApiService.getBreathingDetails("", "", "", "")
            } returns expected

            val response = repository.getBreathingDetails("", "", "", "")

            coVerify { trackingApiService.getBreathingDetails("", "", "", "") }
            assert(response is ResponseState.Success)
            assertEquals((response as ResponseState.Success).data, expected)
        }

        @Test
        fun `getBreathingDetails, returns Error`() = runTest {

            coEvery {
                trackingApiService.getBreathingDetails("", "", "", "")
            } throws Exception()

            val response = repository.getBreathingDetails("", "", "", "")

            coVerify { trackingApiService.getBreathingDetails("", "", "", "") }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Sleep Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetSleepDetails {

        @Test
        fun `getSleepDetails, returns Success`() = runTest {

            val expected = mockk<SleepResponse>()

            coEvery {
                trackingApiService.getSleepDetails("", "", "", "")
            } returns expected

            val response = repository.getSleepDetails("", "", "", "")

            coVerify { trackingApiService.getSleepDetails("", "", "", "") }
            assert(response is ResponseState.Success)
            assertEquals((response as ResponseState.Success).data, expected)
        }

        @Test
        fun `getSleepDetails, returns Error`() = runTest {

            coEvery {
                trackingApiService.getSleepDetails("", "", "", "")
            } throws Exception()

            val response = repository.getSleepDetails("", "", "", "")

            coVerify { trackingApiService.getSleepDetails("", "", "", "") }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Sunlight Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetSunlightDetails {

        @Test
        fun `getSunlightDetails, returns Success`() = runTest {

            val expected = mockk<SunlightResponse>()

            coEvery {
                trackingApiService.getSunlightDetails("", "", "", "")
            } returns expected

            val response = repository.getSunlightDetails("", "", "", "")

            coVerify { trackingApiService.getSunlightDetails("", "", "", "") }
            assert(response is ResponseState.Success)
            assertEquals((response as ResponseState.Success).data, expected)
        }

        @Test
        fun `getSunlightDetails, returns Error`() = runTest {

            coEvery {
                trackingApiService.getSunlightDetails("", "", "", "")
            } throws Exception()

            val response = repository.getSunlightDetails("", "", "", "")

            coVerify { trackingApiService.getSunlightDetails("", "", "", "") }
            assert(response is ResponseState.ErrorMessage)
        }
    }


    @Nested
    @DisplayName("Get Exercise Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetExerciseDetails {

        @Test
        fun `getExerciseDetails, returns Success`() = runTest {

            val expected = mockk<ExerciseResponse>()

            coEvery {
                trackingApiService.getExerciseDetails("", "", "", "", "")
            } returns expected

            val response = repository.getExerciseDetails("", "", "", "", "")

            coVerify { trackingApiService.getExerciseDetails("", "", "", "", "") }
            assert(response is ResponseState.Success)
            assertEquals((response as ResponseState.Success).data, expected)
        }

        @Test
        fun `getExerciseDetails, returns Error`() = runTest {

            coEvery {
                trackingApiService.getExerciseDetails("", "", "", "", "")
            } throws Exception()

            val response = repository.getExerciseDetails("", "", "", "", "")

            coVerify { trackingApiService.getExerciseDetails("", "", "", "", "") }
            assert(response is ResponseState.ErrorMessage)
        }
    }
}