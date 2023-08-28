package fit.asta.health.referral.vm

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.referral.remote.model.ReferralDataResponse
import fit.asta.health.referral.repo.ReferralRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReferralViewModelTest : BaseTest() {

    private lateinit var viewModel: ReferralViewModel

    @RelaxedMockK
    lateinit var repo: ReferralRepo

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        MockKAnnotations.init(this)
        viewModel = spyk(
            ReferralViewModel(
                repo,
                ""
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
    }

    @Test
    fun `getData with error, return error`() = runTest {
        coEvery { repo.getData(any()) } returns ResponseState.Error(Exception())

        viewModel.getData()

        coVerify { repo.getData("") }

        viewModel.state.test {
            assert(awaitItem() is UiState.Error)
        }
    }

    @Test
    fun `getData no error, return success`() = runTest {
        coEvery { repo.getData(any()) } returns ResponseState.Success(ReferralDataResponse())
        viewModel.getData()

        coVerify { repo.getData("") }

        viewModel.state.test {
            assert(awaitItem() is UiState.Success)
        }
    }
}