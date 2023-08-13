package fit.asta.health.payments.referral.vm

import com.google.common.truth.Truth
import fit.asta.health.common.utils.ResponseState
import health.payments.referral.repo.FakeReferralRepoImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReferralViewModelTest {

    private lateinit var viewModel: ReferralViewModel
    private val repo = FakeReferralRepoImpl()

    @BeforeEach
    fun setup() {
        viewModel = ReferralViewModel(
            repo, ""
        )
    }

    @Test
    fun `getData with error, return error`() {
        repo.setError(true)
        viewModel.getData()
        Truth.assertThat(viewModel.state.value).isInstanceOf(ResponseState.Error::class.java)
    }

    @Test
    fun `getData no error, return success`() {
        repo.setError(false)
        viewModel.getData()
        Truth.assertThat(viewModel.state.value).isInstanceOf(ResponseState.Success::class.java)
    }
}