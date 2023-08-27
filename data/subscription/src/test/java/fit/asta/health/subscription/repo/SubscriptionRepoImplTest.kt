package fit.asta.health.subscription.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.subscription.remote.SubscriptionApi
import fit.asta.health.subscription.remote.model.SubscriptionResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SubscriptionRepoImplTest {

    private lateinit var repo: SubscriptionRepoImpl

    @RelaxedMockK
    lateinit var api: SubscriptionApi


    @BeforeEach
    fun beforeEach() {
        MockKAnnotations.init(this, relaxed = true)
        repo = spyk(
            SubscriptionRepoImpl(
                remoteApi = api,
                coroutineDispatcher = UnconfinedTestDispatcher()
            )
        )
    }

    @Test
    fun `getData, returns Success`() = runTest {
        coEvery { api.getData(any(), any()) } returns SubscriptionResponse()
        val response = repo.getData("", "")
        coVerify { api.getData("", "") }
        assert(response is ResponseState.Success)
    }

    @Test
    fun `getData with random exception, return Error Response`() = runTest {
        coEvery { api.getData(any(), any()) } throws Exception()
        val response = repo.getData("", "")
        coVerify { api.getData("", "") }
        assert(response is ResponseState.Error)
    }
}