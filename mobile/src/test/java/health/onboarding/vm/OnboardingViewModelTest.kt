package health.onboarding.vm

import MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import fit.asta.health.common.utils.UiState
import fit.asta.health.onboarding.data.repo.OnboardingRepoImpl
import fit.asta.health.onboarding.ui.vm.OnboardingViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OnboardingViewModelTest {

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: OnboardingViewModel

    @MockK
    private lateinit var repo: OnboardingRepoImpl

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = OnboardingViewModel(
            repo,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `getData with error, return error`() = runTest {
        coEvery { repo.getData() } returns flowOf(UiState.Error(0))
        viewModel.getData()
        coVerify { repo.getData() }
        assertThat(viewModel.state.value).isInstanceOf(UiState.Error::class.java)
    }
//
//    @Test
//    fun `getData no error, return success`() {
//        repo.setError(false)
//        viewModel.getData()
//        assertThat(viewModel.state.value).isInstanceOf(ResponseState.Success::class.java)
//    }
//
//    @Test
//    fun `getData no error, return data list`() {
//        repo.setError(false)
//        viewModel.getData()
//        if (viewModel.state.value is ResponseState.Success) {
//            val data = (viewModel.state.value as ResponseState.Success).data
//            assertThat(data).isNotEmpty()
//        }
//    }
}