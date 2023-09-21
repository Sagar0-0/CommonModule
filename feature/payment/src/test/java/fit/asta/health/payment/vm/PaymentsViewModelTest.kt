package fit.asta.health.payment.vm

import app.cash.turbine.test
import fit.asta.health.auth.model.domain.User
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.payment.remote.model.OrderResponse
import fit.asta.health.payment.repo.PaymentsRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PaymentsViewModelTest : BaseTest() {

    private lateinit var viewModel: PaymentsViewModel

    @RelaxedMockK
    lateinit var repo: PaymentsRepo

    @RelaxedMockK
    lateinit var auth: AuthRepo

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        MockKAnnotations.init(this)
        viewModel = spyk(
            PaymentsViewModel(
                repo,
                auth,
                ""
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
    }

    @Test
    fun `createOrder with error, return error`() = runTest {
        coEvery { repo.createOrder(any()) } returns ResponseState.ErrorMessage(mockk())

        val request = OrderRequest(country = "india", type = 1)
        viewModel.createOrder(request)

        coVerify { repo.createOrder(request) }

        viewModel.orderResponseState.test {
            assert(awaitItem() is UiState.ErrorMessage)
        }
    }

    @Test
    fun `createOrder no error, return success`() = runTest {
        coEvery { repo.createOrder(any()) } returns ResponseState.Success(OrderResponse())
        val request = OrderRequest(country = "india", type = 1)
        viewModel.createOrder(request)

        coVerify { repo.createOrder(request) }

        viewModel.orderResponseState.test {
            assert(awaitItem() is UiState.Success)
        }
    }

    @Test
    fun `verifyAndUpdateProfile with error, return error`() = runTest {
        coEvery {
            repo.verifyAndUpdateProfile(
                any(),
                any()
            )
        } returns ResponseState.ErrorMessage(mockk())

        viewModel.verifyAndUpdateProfile("")

        coVerify { repo.verifyAndUpdateProfile("", "") }

        viewModel.paymentResponseState.test {
            assert(awaitItem() is UiState.ErrorMessage)
        }
    }

    @Test
    fun `verifyAndUpdateProfile no error, return success`() = runTest {
        coEvery { repo.verifyAndUpdateProfile(any(), any()) } returns ResponseState.Success(
            mockk()
        )

        viewModel.verifyAndUpdateProfile("")

        coVerify { repo.verifyAndUpdateProfile("", "") }

        viewModel.paymentResponseState.test {
            assert(awaitItem() is UiState.Success)
        }
    }

    @Test
    fun `currentUser, returns User`() = runTest {
        val user = User()
        coEvery { auth.getUser() } returns user
        viewModel = PaymentsViewModel(repo, auth, "")
        assertEquals(viewModel.currentUser, user)
    }

}