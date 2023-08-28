package fit.asta.health.payment.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payment.remote.PaymentsApi
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.payment.remote.model.OrderResponse
import fit.asta.health.payment.remote.model.PaymentResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PaymentsRepoImplTest {

    private lateinit var repo: PaymentsRepoImpl

    @RelaxedMockK
    lateinit var api: PaymentsApi


    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        repo = spyk(
            PaymentsRepoImpl(
                remoteApi = api,
                coroutineDispatcher = UnconfinedTestDispatcher()
            )
        )
    }

    @Test
    fun `createOrder, returns Success`() = runTest {
        coEvery { api.createOrder(any()) } returns OrderResponse()
        val response = repo.createOrder(OrderRequest())
        coVerify { api.createOrder(OrderRequest()) }
        assert(response is ResponseState.Success)
    }

    @Test
    fun `createOrder with random exception, return Error Response`() = runTest {
        coEvery { api.createOrder(any()) } throws Exception()
        val response = repo.createOrder(OrderRequest())
        coVerify { api.createOrder(OrderRequest()) }
        assert(response is ResponseState.Error)
    }

    @Test
    fun `verifyAndUpdateProfile, returns Success`() = runTest {
        coEvery { api.verifyAndUpdateProfile(any(), any()) } returns PaymentResponse()
        val response = repo.verifyAndUpdateProfile("", "")
        coVerify { api.verifyAndUpdateProfile("", "") }
        assert(response is ResponseState.Success)
    }

    @Test
    fun `verifyAndUpdateProfile with random exception, return Error Response`() = runTest {
        coEvery { api.verifyAndUpdateProfile(any(), any()) } throws Exception()
        val response = repo.verifyAndUpdateProfile("", "")
        coVerify { api.verifyAndUpdateProfile("", "") }
        assert(response is ResponseState.Error)
    }
}