package fit.asta.health.data.orders.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.orders.remote.OrdersApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrdersRepoImplTest {

    private lateinit var onboardingRepoImpl: OrdersRepoImpl

    @RelaxedMockK
    lateinit var ordersApi: OrdersApi


    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        onboardingRepoImpl = spyk(
            OrdersRepoImpl(
                ordersApi = ordersApi,
                coroutineDispatcher = UnconfinedTestDispatcher()
            )
        )
    }

    @Test
    fun `getData with success, return Success Response`() = runTest {
        coEvery { ordersApi.getOrders(any()) } returns mockk()
        val response = onboardingRepoImpl.getOrders("")
        coVerify { ordersApi.getOrders("") }
        assert(response is ResponseState.Success)
    }

    @Test
    fun `getData with random exception, return Error Response`() = runTest {
        coEvery { ordersApi.getOrders(any()) } throws Exception()
        val response = onboardingRepoImpl.getOrders("")
        coVerify { ordersApi.getOrders("") }
        assert(response is ResponseState.ErrorMessage)
    }
}