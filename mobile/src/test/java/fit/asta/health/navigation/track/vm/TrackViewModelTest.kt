package fit.asta.health.navigation.track.vm

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.navigation.track.data.remote.model.breathing.BreathingResponse
import fit.asta.health.navigation.track.data.remote.model.exercise.ExerciseResponse
import fit.asta.health.navigation.track.data.remote.model.meditation.MeditationResponse
import fit.asta.health.navigation.track.data.remote.model.menu.HomeMenuResponse
import fit.asta.health.navigation.track.data.remote.model.sleep.SleepResponse
import fit.asta.health.navigation.track.data.remote.model.step.StepsResponse
import fit.asta.health.navigation.track.data.remote.model.sunlight.SunlightResponse
import fit.asta.health.navigation.track.data.remote.model.water.WaterResponse
import fit.asta.health.navigation.track.data.repo.TrackingRepo
import fit.asta.health.navigation.track.ui.util.TrackOption
import fit.asta.health.navigation.track.ui.util.TrackUiEvent
import fit.asta.health.navigation.track.ui.viewmodel.TrackViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

class TrackViewModelTest : BaseTest() {

    private lateinit var viewModel: TrackViewModel
    private val trackingRepo: TrackingRepo = mockk(relaxed = true)

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewModel = spyk(TrackViewModel(trackingRepo))
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Nested
    @DisplayName("Get Home Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetHomeDetails {


        @Test
        fun `getHomeDetails, return Success`() = runTest {
            val expectedResponse = mockk<HomeMenuResponse>()
            coEvery {
                trackingRepo.getHomeDetails(any(), any(), any(), any())
            } returns ResponseState.Success(expectedResponse)

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.HomeMenuOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getHomeDetails(any(), any(), any(), any()) }

            viewModel.homeScreenDetails.test {
                assert(awaitItem() is UiState.Success)
            }
        }


        @Test
        fun `getHomeDetails, return error`() = runTest {
            coEvery {
                trackingRepo.getHomeDetails(any(), any(), any(), any())
            } returns ResponseState.Error(Exception())

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.HomeMenuOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getHomeDetails(any(), any(), any(), any()) }

            viewModel.homeScreenDetails.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Water Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetWaterDetails {

        @Test
        fun `getWaterDetails, return Success`() = runTest {
            val expectedResponse = mockk<WaterResponse>()

            coEvery {
                trackingRepo.getWaterDetails(any(), any(), any(), any())
            } returns ResponseState.Success(expectedResponse)

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.WaterOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getWaterDetails(any(), any(), any(), any()) }

            viewModel.waterDetails.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getWaterDetails, return Error`() = runTest {
            coEvery {
                trackingRepo.getWaterDetails(any(), any(), any(), any())
            } returns ResponseState.Error(Exception())

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.WaterOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getWaterDetails(any(), any(), any(), any()) }

            viewModel.waterDetails.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Steps Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetStepsDetails {

        @Test
        fun `getStepsDetails, return Success`() = runTest {
            val expectedResponse = mockk<StepsResponse>()

            coEvery {
                trackingRepo.getStepsDetails(any(), any(), any(), any())
            } returns ResponseState.Success(expectedResponse)

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.StepsOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getStepsDetails(any(), any(), any(), any()) }

            viewModel.stepsDetails.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getStepsDetails, return Error`() = runTest {
            coEvery {
                trackingRepo.getStepsDetails(any(), any(), any(), any())
            } returns ResponseState.Error(Exception())

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.StepsOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getStepsDetails(any(), any(), any(), any()) }

            viewModel.stepsDetails.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Meditation Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetMeditationDetails {

        @Test
        fun `getMeditationDetails, return Success`() = runTest {
            val expectedResponse = mockk<MeditationResponse>()

            coEvery {
                trackingRepo.getMeditationDetails(any(), any(), any(), any())
            } returns ResponseState.Success(expectedResponse)

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.MeditationOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getMeditationDetails(any(), any(), any(), any()) }

            viewModel.meditationDetails.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getMeditationDetails, return Error`() = runTest {
            coEvery {
                trackingRepo.getMeditationDetails(any(), any(), any(), any())
            } returns ResponseState.Error(Exception())

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.MeditationOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getMeditationDetails(any(), any(), any(), any()) }

            viewModel.meditationDetails.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Breathing Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBreathingDetails {

        @Test
        fun `getBreathingDetails, return Success`() = runTest {
            val expectedResponse = mockk<BreathingResponse>()

            coEvery {
                trackingRepo.getBreathingDetails(any(), any(), any(), any())
            } returns ResponseState.Success(expectedResponse)

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.BreathingOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getBreathingDetails(any(), any(), any(), any()) }

            viewModel.breathingDetails.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getBreathingDetails, return Error`() = runTest {
            coEvery {
                trackingRepo.getBreathingDetails(any(), any(), any(), any())
            } returns ResponseState.Error(Exception())

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.BreathingOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getBreathingDetails(any(), any(), any(), any()) }

            viewModel.breathingDetails.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Sleep Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetSleepDetails {

        @Test
        fun `getSleepDetails, return Success`() = runTest {
            val expectedResponse = mockk<SleepResponse>()

            coEvery {
                trackingRepo.getSleepDetails(any(), any(), any(), any())
            } returns ResponseState.Success(expectedResponse)

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.SleepOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getSleepDetails(any(), any(), any(), any()) }

            viewModel.sleepDetails.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getSleepDetails, return Error`() = runTest {
            coEvery {
                trackingRepo.getSleepDetails(any(), any(), any(), any())
            } returns ResponseState.Error(Exception())

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.SleepOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getSleepDetails(any(), any(), any(), any()) }

            viewModel.sleepDetails.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Sunlight Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetSunlightDetails {

        @Test
        fun `getSunlightDetails, return Success`() = runTest {
            val expectedResponse = mockk<SunlightResponse>()

            coEvery {
                trackingRepo.getSunlightDetails(any(), any(), any(), any())
            } returns ResponseState.Success(expectedResponse)

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.SunlightOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getSunlightDetails(any(), any(), any(), any()) }

            viewModel.sunlightDetails.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getSunlightDetails, return Error`() = runTest {
            coEvery {
                trackingRepo.getSunlightDetails(any(), any(), any(), any())
            } returns ResponseState.Error(Exception())

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.SunlightOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getSunlightDetails(any(), any(), any(), any()) }

            viewModel.sunlightDetails.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }


    @Nested
    @DisplayName("Get Exercise Details")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetExerciseDetails {

        @Test
        fun `getExerciseDetails, return Success`() = runTest {
            val expectedResponse = mockk<ExerciseResponse>()

            coEvery {
                trackingRepo.getExerciseDetails(any(), any(), any(), any(), any())
            } returns ResponseState.Success(expectedResponse)

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.YogaOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getExerciseDetails(any(), any(), any(), any(), any()) }

            viewModel.exerciseDetails.test {
                assert(awaitItem() is UiState.Success)
            }
        }

        @Test
        fun `getExerciseDetails, return Error`() = runTest {
            coEvery {
                trackingRepo.getExerciseDetails(any(), any(), any(), any(), any())
            } returns ResponseState.Error(Exception())

            viewModel.uiEventListener(TrackUiEvent.SetTrackOption(TrackOption.YogaOption))
            viewModel.uiEventListener(TrackUiEvent.SetTrackStatus(0))

            coVerify { trackingRepo.getExerciseDetails(any(), any(), any(), any(), any()) }

            viewModel.exerciseDetails.test {
                assert(awaitItem() is UiState.Error)
            }
        }
    }
}