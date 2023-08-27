package fit.asta.health.feature.orders.vm

import app.cash.turbine.test
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.core.test.BaseTest
import fit.asta.health.data.orders.remote.model.OrdersDTO
import fit.asta.health.data.orders.repo.OrdersRepoImpl
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrdersViewModelTest : BaseTest() {

    private lateinit var viewModel: OrdersViewModel

    private val repo: OrdersRepoImpl = mockk(relaxed = true)

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        viewModel = spyk(
            OrdersViewModel(
                repo,
                ""
            )
        )
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Test
    fun `getOrders with error, return error`() = runTest {
        coEvery { repo.getOrders(any()) } returns ResponseState.Error(Exception())

        viewModel.getOrders()

        coVerify { repo.getOrders("") }

        viewModel.ordersState.test {
            assert(awaitItem() is UiState.Error)
        }
    }

    @Test
    fun `getOrders no error, return success`() = runTest {
        coEvery { repo.getOrders(any()) } returns ResponseState.Success(OrdersDTO())

        viewModel.getOrders()

        coVerify { repo.getOrders("") }

        viewModel.ordersState.test {
            assert(awaitItem() is UiState.Success)
        }
    }
}