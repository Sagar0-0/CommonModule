package fit.asta.health.feature.onboarding

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.data.onboarding.model.OnboardingData
import fit.asta.health.data.onboarding.repo.OnboardingRepoImpl
import fit.asta.health.feature.onboarding.vm.OnboardingViewModel
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OnboardingViewModelTest : BaseTest() {

    private lateinit var viewModel: OnboardingViewModel

    private val onboardingRepoImpl: OnboardingRepoImpl = mockk(relaxed = true)

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewModel = spyk(
            OnboardingViewModel(
                onboardingRepoImpl
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Test
    fun `getData with error, return error`() = runTest {
        coEvery { onboardingRepoImpl.getData() } returns ResponseState.ErrorMessage(mockk())

        viewModel.getData()

        coVerify { onboardingRepoImpl.getData() }

        viewModel.state.test {
            assert(awaitItem() is UiState.ErrorMessage)
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

    @Test
    fun `dismissOnboarding, calls Repo`() = runTest {
        coEvery {
            onboardingRepoImpl.setOnboardingDone()
        } just Runs
        viewModel.navigateToAuth()
        coVerify { onboardingRepoImpl.setOnboardingDone() }
    }
}