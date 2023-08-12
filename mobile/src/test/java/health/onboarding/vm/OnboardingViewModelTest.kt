package health.onboarding.vm

import app.cash.turbine.test
import fit.asta.health.auth.data.repo.AuthRepoImpl
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.onboarding.data.model.OnboardingData
import fit.asta.health.onboarding.data.repo.OnboardingRepoImpl
import fit.asta.health.onboarding.ui.vm.OnboardingViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OnboardingViewModelTest {

    private lateinit var viewModel: OnboardingViewModel

    private val onboardingRepoImpl: OnboardingRepoImpl = mockk(relaxed = true)
    private val authRepo: AuthRepoImpl = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        viewModel = spyk(
            OnboardingViewModel(
                onboardingRepoImpl,
                authRepo
            )
        )
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `getData with error, return error`() = runTest {
        coEvery { onboardingRepoImpl.getData() } returns ResponseState.Error(Exception())

        viewModel.getData()

        coVerify { onboardingRepoImpl.getData() }

        viewModel.state.test {
            assert(awaitItem() is UiState.Error)
        }
    }

    @Test
    fun `getData no error, return success`() = runTest {
        val mockList = emptyList<OnboardingData>()
        coEvery { onboardingRepoImpl.getData() } returns ResponseState.Success(mockList)

        viewModel.getData()

        coVerify { onboardingRepoImpl.getData() }

        viewModel.state.test {
            assert(awaitItem() is UiState.Success)
        }
    }

    @Test
    fun `getData no error, return data list`() = runTest {
        val mockList = listOf(OnboardingData(), OnboardingData())
        coEvery { onboardingRepoImpl.getData() } returns ResponseState.Success(mockList)

        viewModel.getData()

        coVerify { onboardingRepoImpl.getData() }

        viewModel.state.test {
            val item = awaitItem()
            assert(item is UiState.Success)
            assertEquals(mockList.size, (item as UiState.Success).data.size)
        }
    }
}