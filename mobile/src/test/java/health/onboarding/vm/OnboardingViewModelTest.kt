package health.onboarding.vm

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.onboarding.data.modal.OnboardingData
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

    private val repo: OnboardingRepoImpl = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        viewModel = spyk(
            OnboardingViewModel(
                repo
            )
        )
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `getData with error, return error`() = runTest {
        coEvery { repo.getData() } returns ResponseState.Error(Exception())

        viewModel.getData()

        coVerify { repo.getData() }

        viewModel.state.test {
            assert(awaitItem() is UiState.Error)
        }
    }

    @Test
    fun `getData no error, return success`() = runTest {
        val mockList = emptyList<OnboardingData>()
        coEvery { repo.getData() } returns ResponseState.Success(mockList)

        viewModel.getData()

        coVerify { repo.getData() }

        viewModel.state.test {
            assert(awaitItem() is UiState.Success)
        }
    }

    @Test
    fun `getData no error, return data list`() = runTest {
        val mockList = listOf(OnboardingData(), OnboardingData())
        coEvery { repo.getData() } returns ResponseState.Success(mockList)

        viewModel.getData()

        coVerify { repo.getData() }

        viewModel.state.test {
            val item = awaitItem()
            assert(item is UiState.Success)
            assertEquals(mockList.size, (item as UiState.Success).data.size)
        }
    }
}