package fit.asta.health.wallet.vm

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.wallet.remote.model.WalletResponse
import fit.asta.health.wallet.repo.WalletRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WalletViewModelTest : BaseTest() {

    private lateinit var viewModel: WalletViewModel

    @RelaxedMockK
    lateinit var repo: WalletRepo


    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        MockKAnnotations.init(this)
        viewModel = spyk(
            WalletViewModel(
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
        coEvery { repo.getData(any()) } returns ResponseState.Success(WalletResponse())
        viewModel.getData()

        coVerify { repo.getData("") }

        viewModel.state.test {
            assert(awaitItem() is UiState.Success)
        }
    }
}