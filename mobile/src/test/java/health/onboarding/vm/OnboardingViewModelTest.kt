package health.onboarding.vm

import CoroutinesTestExtension
import InstantExecutorExtension
import com.google.common.truth.Truth.assertThat
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.onboarding.repo.FakeOnboardingRepoImpl
import fit.asta.health.onboarding.vm.OnboardingViewModel
import health.network.FakeNetworkHelperImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantExecutorExtension::class, CoroutinesTestExtension::class)
class OnboardingViewModelTest {
    private lateinit var viewModel: OnboardingViewModel
    private val repo = FakeOnboardingRepoImpl()

    @BeforeEach
    fun setup() {
        viewModel = OnboardingViewModel(
            repo,
            FakeNetworkHelperImpl()
        )
    }

    @Test
    fun `getData with error, return error`() {
        repo.setError(true)
        viewModel.getData()
        assertThat(viewModel.state.value).isInstanceOf(ResponseState.Error::class.java)
    }

    @Test
    fun `getData no error, return success`() {
        repo.setError(false)
        viewModel.getData()
        assertThat(viewModel.state.value).isInstanceOf(ResponseState.Success::class.java)
    }

    @Test
    fun `getData no error, return data list`() {
        repo.setError(false)
        viewModel.getData()
        if (viewModel.state.value is ResponseState.Success) {
            val data = (viewModel.state.value as ResponseState.Success).data
            assertThat(data).isNotEmpty()
        }
    }
}